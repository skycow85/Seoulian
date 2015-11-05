package kr.go.seoul.seoulian.Http;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by jkwoo on 2015-09-15.
 */
public class EzTrustManager implements X509TrustManager {

    private X509TrustManager standardTrustManager = null;

    public EzTrustManager(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException {
        super();
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore);
        TrustManager[] trustManagers = factory.getTrustManagers();
        if(trustManagers.length == 0){
            throw new NoSuchAlgorithmException("no trust manager");
        }
        this.standardTrustManager = (X509TrustManager) trustManagers[0];
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        standardTrustManager.checkClientTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // 자격증명을 삭제하고 새로 만듦
        // 이론적으로, 이건 불가능하지만 많은 웹서버들이
        // 잘못된 자격증명을 가지고 있거나 내부적 이슈가 있거나 만료된 인증으로 인해 잘못 설정되어있다.
        // Clean up the certificates chain and build a new one.
        // Theoretically, we shouldn't have to do this, but various web servers
        // in practice are mis-configured to have out-of-order certificates or
        // expired self-issued root certificate.
        int chainLength = chain.length;
        if(chainLength > 1){
            // 1. 받은 자격증명 연결을 제거한다.
            //    자격증명의 끝부분에서 시작하여 더이상 불가능할때까지 issuer 필드와 subject 필드를 매칭시켜 추적한다
            //    이 작업은 자격증명이 고장났거나 몇 자격증명들이 사이트와 관계가 없을 때 도움이 된다.
            // 1. we clean the received certificates chain.
            //    We start from the end-entity certificate, tracing down by matching
            //    the "issuer" field and "subject" field until we can't continue.
            //    This helps when the certificates are out of order or
            //    some certificates are not related to the site.
            int curIndex;
            for(curIndex = 0; curIndex < chainLength; ++curIndex){
                boolean foundNext = false;
                for(int nextIndex = curIndex+1; nextIndex < chainLength; ++nextIndex){
                    if(chain[curIndex].getIssuerDN().equals(chain[nextIndex].getSubjectDN())){
                        foundNext = true;
                        // 0 부터 curIndex+1 까지 자격증명이 제대로된 순서가 되도록 교환한다
                        // Exchange certificates so that 0 through currIndex + 1 are in proper order
                        if(nextIndex != curIndex+1){
                            X509Certificate tmpCertificate = chain[nextIndex];
                            chain[nextIndex] = chain[curIndex+1];
                            chain[curIndex+1] = tmpCertificate;
                        }
                        break;
                    }
                }
                if(!foundNext){
                    break;
                }
            }
            // 2. 마지막 추적한 자격증명이 내부적인 문제가 있거나 만료되었는지 시험한다.
            //    만약 그럴 경우, 이것은 제거하고 우리가 비슷하지만 만료되지 않은 root를 가지도록 바라며
            //    나머지는 checkServerTrusted()로 통과시킨다.
            // 2. we exam if the last traced certificate is self issued and it is expired.
            //    If so, we drop it and pass the rest to checkServerTrusted(), hoping we might
            //    have a similar but unexpired trusted root.
            chainLength = curIndex+1;
            X509Certificate lastCertificate = chain[chainLength-1];
            Date now = new Date();
            if(lastCertificate.getSubjectDN().equals(lastCertificate.getIssuerDN()) && now.after(lastCertificate.getNotAfter())){
                --chainLength;
            }
        }

        standardTrustManager.checkClientTrusted(chain, authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return this.standardTrustManager.getAcceptedIssuers();
    }
}
