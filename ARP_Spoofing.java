import java.util.*;


class Arp {

    int ip;

    int mac;

    Map<Integer, Integer> arptable;
    List<String> list = new ArrayList<>();

    // constructor
    public Arp(int mac, int ip) {
        // TODO
        this.ip = ip;
        this.mac = mac;
        arptable = new HashMap<>();
        arptable.put(mac, ip);

    }

    // This function returns a spoofed ARP packet:
    //  The argument passed to this function is the IP address that you want to impersonate.
    public String spoofArp(int spoofIP) {
        String spoofedMac = Integer.toHexString(mac);
        String spoofedIP = Integer.toHexString(spoofIP);

        if(!arptable.containsValue(Integer.parseInt(spoofedIP, 16))){
            return "02" + spoofedMac + spoofedIP + "000000" + spoofedIP;
        }
        else{
            return "02" + spoofedMac + spoofedIP + getMacAddress(spoofedIP) + spoofedIP;
        }

    }

    // Receive a message and provide the response. This function returns either a packet, or a status code.
    public String receiveArp(String message) {

        String opcode = message.substring(0,2);
        String senderMac = message.substring(2,8);
        String senderIp = message.substring(8,12);
        String targetMac = message.substring(12,18);
        String targetIp = message.substring(18,22);

        String ignoreMessage = "IGNORE";
        String okMessage = "OK";
        String attackMessage = "ATTACK";

        String result = "";

        if(message.length() < 22 || message.length() > 22){
            result = ignoreMessage;
        }

        if(opcode.equals("01")) {
            if(senderIp != targetIp && arptable.containsValue(hexToInt(targetIp))) {
                result = "02" + getMacAddress(targetIp) + targetIp + senderMac + senderIp;
            }
            if(senderIp != targetIp && !arptable.containsValue(hexToInt(targetIp))) {
                result = ignoreMessage;
            }
            if(senderIp.equals(targetIp) && arptable.containsValue(hexToInt(targetIp))){
                result = ignoreMessage;
            }
        }

        if(opcode.equals("02")){
            if(targetMac != "000000"){
                result = ignoreMessage;
            }

            if(senderIp.equals(targetIp) && arptable.containsValue(hexToInt(targetIp))){
                if(!getMacAddress(targetIp).equals(senderMac)){
                    result = attackMessage;
                }else{
                    arptable.put(hexToInt(senderMac), hexToInt(senderIp));
                    result = ignoreMessage;
                }
            }

            if(senderIp.equals(targetIp) && !arptable.containsValue(hexToInt(targetIp))){
                arptable.put(hexToInt(senderMac), hexToInt(senderIp));
                result = okMessage;
            }


        }

        System.out.println("opcode " + opcode);
        System.out.println("senderMac " + senderMac);
        System.out.println("senderIp " + senderIp);
        System.out.println("targetMac " + targetMac);
        System.out.println("targetIp " + targetIp);
        System.out.println("int targetIp " + hexToInt(targetIp));
        System.out.println("int senderIp " + hexToInt(senderIp));
        System.out.println("arp table ip " + getMacAddress(targetIp));
        System.out.println("arp table mac " + getMacAddress(targetMac));

        System.out.println("mac in arp table " + mac);
        System.out.println("ip in arp table " + ip);

        return result;

    }

    public String getMacAddress(String ip){
        String mac = "";
        int ipToInt = Integer.parseInt(ip, 16);
        for(Map.Entry table: arptable.entrySet()){
            if((int)table.getValue() == ipToInt){
                int key = (int)table.getKey();
                mac = Integer.toHexString(key);
            }
        }
        return mac;
    }

    public int hexToInt(String hex){
        int result;
        result = Integer.parseInt(hex, 16);
        return result;
    }


}

