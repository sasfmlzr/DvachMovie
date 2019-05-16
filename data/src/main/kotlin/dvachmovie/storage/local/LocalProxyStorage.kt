package dvachmovie.storage.local

import dvachmovie.storage.ProxyStorage
import dvachmovie.storage.SettingsStorage
import java.net.InetSocketAddress
import java.net.Proxy
import javax.inject.Inject

class LocalProxyStorage @Inject constructor(private val settingsStorage: SettingsStorage) : ProxyStorage {
    override fun getProxy(): Proxy? {
        if (settingsStorage.getProxyUrl() == "") {
            return null
        }
        return Proxy(Proxy.Type.HTTP, InetSocketAddress(settingsStorage.getProxyUrl(),
                settingsStorage.getProxyPort()))
    }
}
