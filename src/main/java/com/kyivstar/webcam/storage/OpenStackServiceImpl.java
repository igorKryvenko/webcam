package com.kyivstar.webcam.storage;

import com.kyivstar.webcam.configuration.properties.OpenStackProperties;
import com.kyivstar.webcam.exception.StorageServiceException;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.types.Facing;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.payloads.InputStreamPayload;
import org.openstack4j.model.identity.v3.Token;
import org.openstack4j.model.storage.object.options.ObjectPutOptions;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by igor on 20.09.17.
 */
@Service
@EnableConfigurationProperties(OpenStackProperties.class)
public class OpenStackServiceImpl implements OpenStackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenStackServiceImpl.class);

    @Autowired
    private OpenStackProperties openstackProperties;
    private Identifier domainIdentifier;


    protected final AtomicReference<Token> arToken = new AtomicReference<>(null);

    @PostConstruct
    public void init() {
        LOGGER.debug("INIT OpenStack Properties : BEGIN");

        domainIdentifier = Identifier.byName(openstackProperties.getIdentity().getDomain());
        Boolean enableHttpLoggingFilter = openstackProperties.getEnableHttpLoggingFilter();

        OSFactory.enableHttpLoggingFilter(enableHttpLoggingFilter);
        createContainer(getClient(), openstackProperties.getSwift().getContainer());
        LOGGER.debug("INIT OpenStack Properties : END");
    }

    protected OSClient.OSClientV3 getClient() {
        final Token token = arToken.get();
        if (null == token)
            return auth();
        else
            return OSFactory.clientFromToken(token);
    }

    protected OSClient.OSClientV3 auth() {
        final OSClient.OSClientV3 os;
        try {
            os = OSFactory
                    .builderV3()
                    .endpoint(openstackProperties.getHost().getAuth())
                    .perspective(Facing.PUBLIC)
                    .credentials(openstackProperties.getIdentity().getUsername(),
                            openstackProperties.getIdentity().getPassword(), domainIdentifier)
                    .authenticate();
        } catch (Exception e) {
            LOGGER.error("OpenStack authentication failed.", e);
            throw new StorageServiceException("Cant auth", e);
        }


        arToken.set(os.getToken());
        return os;
    }
    protected void createContainer(final OSClient.OSClientV3 os, final String containerName) {
        final ActionResponse ar = os
                .objectStorage()
                .containers()
                .create(containerName);
        if (!ar.isSuccess()) {
            throw new StorageServiceException("Cant create container: " + ar.getFault());
        }
    }

    @Override
    public String upload(String storeKey, InputStream inputStream, String contentType) {
        try (final InputStreamPayload fp = new InputStreamPayload(inputStream)) {
            return getClient()
                    .objectStorage()
                    .objects()
                    .put(
                            openstackProperties.getSwift().getContainer(),
                            storeKey,
                            fp,
                            ObjectPutOptions
                                    .create()
                                    .contentType(contentType)
                    );
        } catch (Exception e) {
            LOGGER.error("Put file to storage failed.", e);
            throw new StorageServiceException("Couldn't put file to storage.", e);
        }
    }
    @Override
    public void deleteObject(String storeKey) {
        final ActionResponse ar;
        try {
            ar = getClient()
                    .objectStorage()
                    .objects()
                    .delete(openstackProperties.getSwift().getContainer(), storeKey);
        } catch (Exception e) {
            LOGGER.error("Delete file from storage filed.", e);
            throw new StorageServiceException("Couldn't delete file from storage.", e);
        }
        if (!ar.isSuccess()) {
            throw new StorageServiceException("Cant delete object: " + ar.getFault());
        }
    }
    @Scheduled(fixedRate = 1800000)
    public void tokenRefresh() {
        try {
            auth();
        } catch (StorageServiceException e) {
            LOGGER.error("Refresh token scheduler exception.", e);
        }
    }
    @Override
    public DLPayload getObjectPayload(String storeKey) {

        try {
            DLPayload resPayload = getClient()
                    .objectStorage()
                    .objects()
                    .download(openstackProperties.getSwift().getContainer(), storeKey);

            return resPayload;
        } catch (Exception e) {
            LOGGER.error("Download file from storage failed.", e);
            throw new StorageServiceException("Couldn't download file from storage.", e);
        }
    }
}
