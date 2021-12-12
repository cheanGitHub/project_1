package cc;

public class Test3 {
    public static void main(String[] args) {
        //"abcd" => "61626364" */
        byte[] bytes = "a".getBytes();
        System.out.println(bytes[0]);
        System.out.println(byteToHex(bytes[0]));
        // "a" -- "97" -- "0b 0110 0001" -- "0x61" -- "0110 0001" -- ""
        // "b" -- "98" -- "0b 0110 0010" -- "0x62" -- "0110 0010" -- ""


        byte[] bytes2 = hexToBytes("abcd");
        System.out.println(bytes2.length);
    }

    /**byte[]转16进制*/
    public static String bytesToHex(byte[] bytes){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            int tempI=bytes[i] & 0xFF;//byte:8bit,int:32bit;高位相与.
            String str = Integer.toHexString(tempI);
            if(str.length()<2){
                sb.append(0).append(str);//长度不足两位，补齐：如16进制的d,用0d表示。
            }else{
                sb.append(str);
            }
        }
        return sb.toString();
    }


    /**16进制转byte[]*/
    public static byte[] hexToBytes(String hex){
        int length = hex.length() / 2;
        byte[] bytes=new byte[length];
        for(int i=0;i<length;i++){
            String tempStr=hex.substring(2*i, 2*i+2);//byte:8bit=4bit+4bit=十六进制位+十六进制位
            bytes[i]=(byte) Integer.parseInt(tempStr, 16);
        }
        return bytes;
    }

    /**
     * 字节转十六进制
     * @param b 需要进行转换的byte字节
     * @return  转换后的Hex字符串
     */
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }

}
