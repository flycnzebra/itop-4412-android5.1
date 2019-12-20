// IServiceAidl.aidl
package xinwei.com.mpapp.aidl;

// Declare any non-default types here with import statements
import xinwei.com.mpapp.aidl.Network;

interface IServiceAidl {

    boolean openMultipleStreams(String magip,in int magport,String dns,in int uid,String phone,String pwd,String token);

    boolean closeMultipleStreams();

    void updateNetwork(in List<Network> network);
}
