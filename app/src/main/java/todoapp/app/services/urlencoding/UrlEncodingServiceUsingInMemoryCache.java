package todoapp.app.services.urlencoding;

import java.util.UUID;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.urlencoding.UrlEncodingService;

/**
 * This wouldn't work over a cluster, obviously...
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
@DomainServiceLayout(
        menuOrder = "" + (Integer.MAX_VALUE - 1)
)
public class UrlEncodingServiceUsingInMemoryCache implements UrlEncodingService {

    private BiMap<UUID,String> cache = HashBiMap.create();

    @Override
    public String decode(final String guidStr) {
        final UUID guid = UUID.fromString(guidStr);
        return cache.get(guid);
    }

    @Override
    public String encode(final String xmlStr) {

        UUID guid = cache.inverse().get(xmlStr);
        if(guid == null) {
            guid = UUID.randomUUID();
            cache.put(guid, xmlStr);
        }

        return guid.toString();
    }
}
