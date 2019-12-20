package xinwei.com.mpapp.aidl;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by nishun on 2019/2/15.
 */

public class Network implements Parcelable{
    private int type;
    private String ip;
    private int flag;
    private int level;

    public int getType() {
        return type;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Network(){}

    public Network(Parcel in) {
        super();
        type = in.readInt();
        ip = in.readString();
        flag = in.readInt();
        level = in.readInt();
    }

    public static final Creator<Network> CREATOR = new Creator<Network>() {
        @Override
        public Network createFromParcel(Parcel in) {
            return new Network(in);
        }

        @Override
        public Network[] newArray(int size) {
            return new Network[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(ip);
        dest.writeInt(flag);
        dest.writeInt(level);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        type = dest.readInt();
        ip=dest.readString();
        flag=dest.readInt();
        level=dest.readInt();
    }

    //方便打印数据
    @Override
    public String toString() {
        return "netType : " + String.valueOf(type) + " , ip : " + ip + " , flag : " + String.valueOf(flag)+ " , level : " + String.valueOf(level);
    }

}