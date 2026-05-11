package demo.util;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Base64;

public class AuthProxyManager {

    private static BrowserMobProxyServer proxy;
    private static int proxyPort;

    public static synchronized void startIfNotRunning() {
        if (proxy != null && proxy.isStarted()) return;

        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);

        final String encoded = Base64.getEncoder().encodeToString("vvss:strugure".getBytes());
        proxy.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo info) {
                String url = info.getUrl();
                System.out.println("[PROXY] Request to: " + url);
                if (url != null && url.contains("scs.ubbcluj.ro")) {
                    request.headers().set("Authorization", "Basic " + encoded);
                    System.out.println("[PROXY] Injected Authorization header");
                }
                return null;
            }
        });

        proxy.start(19091);
        proxyPort = proxy.getPort();
    }

    public static synchronized void stop() {
        if (proxy != null && proxy.isStarted()) {
            proxy.stop();
            proxy = null;
        }
    }
}
