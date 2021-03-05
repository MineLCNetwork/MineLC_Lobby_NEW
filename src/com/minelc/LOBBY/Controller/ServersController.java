package com.minelc.LOBBY.Controller;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;


public class ServersController {
    private static HashMap<String, ServersController> Sv = new HashMap<String, ServersController>();
    private static Gson gson = new Gson();
    private static List<ServersController> Servers = Lists.newArrayList();
    private String name = "";
    private ConfigurationSection config;
    private int interval = 5;
    private int timeout =  1000;
    private boolean online = false;
    private int currentplayers = 0;
    private String BungeeName = "";
    private int maxplayers = 0;
    private String motd = "";
    private String ip = "";

    public static ServersController getServer(String s) {
        if(Sv.containsKey(s))
            return Sv.get(s);

        return new ServersController(s);
    }

    public ServersController(String s) {
        name = s;
        config = ConfigController.getInstance().getServersConfig().getConfigurationSection(s);
        timeout = config.getInt("timeout");
        BungeeName = config.getString("BungeeServerName");
        ip = getIPFromHost(config.getString("ip"));
        Sv.put(s, this);
        Servers.add(this);
    }

    public String getIPFromHost(String host) {
        String ret = host;

        try {
            InetAddress address = InetAddress.getByName(host);
            ret = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public String getName() {
        return name;
    }

    public int getUpdateInterval() {
        return config.getInt("UpdateInterval");
    }

    public String getIP() {
        return ip;
    }

    public boolean isOnline() {
        return online;
    }

    public String getBungeeCordServerName() {
        return BungeeName;
    }

    public int getCurrentPlayers() {
        return currentplayers;
    }

    public int getMaxPlayers() {
        return maxplayers;
    }

    public String getMotd() {
        return motd;
    }

    public int getPort() {
        return config.getInt("port");
    }

    public boolean updatePING() {
        if(interval <= 0) {
            interval = getUpdateInterval();
            return true;
        }
        interval--;
        return false;
    }

    public int getTimeout() {
        return timeout;
    }

    public void ping() throws IOException, ParseException {
        try (Socket socket = new Socket(getIP(), getPort())) {
            OutputStream outputStream;
            DataOutputStream dataOutputStream;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            socket.setSoTimeout(timeout);
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream handshake = new DataOutputStream(b);
            handshake.writeByte(0x00);
            writeVarInt(handshake, 47); //protocol version 4
            writeVarInt(handshake, getIP().length());
            handshake.writeBytes(getIP());
            handshake.writeShort(getPort());
            writeVarInt(handshake, 1);

            writeVarInt(dataOutputStream, b.size());
            dataOutputStream.write(b.toByteArray());

            dataOutputStream.writeByte(0x01);
            dataOutputStream.writeByte(0x00);
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            readVarInt(dataInputStream);
            int id = readVarInt(dataInputStream);

            if (id == -1) {
                online = false;
                currentplayers = 0;
                motd = "";
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                return;
            }

            if (id != 0x00) { //we want a status response
                online = false;
                currentplayers = 0;
                motd = "";
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                return;
            }
            int length = readVarInt(dataInputStream); //length of json string

            if (length == -1) {
                online = false;
                currentplayers = 0;
                motd = "";
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                return;
            }

            if (length == 0) {
                online = false;
                currentplayers = 0;
                motd = "";
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                return;
            }

            byte[] in = new byte[length];
            dataInputStream.readFully(in);  //read json string
            String json = new String(in);


            long now = System.currentTimeMillis();
            dataOutputStream.writeByte(0x09); //size of packet
            dataOutputStream.writeByte(0x01); //0x01 for ping
            dataOutputStream.writeLong(now); //time!?

            readVarInt(dataInputStream);
            id = readVarInt(dataInputStream);
            if (id == -1)  {
                online = false;
                currentplayers = 0;
                motd = "";
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                return;
            }

            if (id != 0x01) {
                online = false;
                currentplayers = 0;
                motd = "";
                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
                return;
            }
            dataInputStream.readLong(); //ping time
            StatusResponse resp = gson.fromJson(json, StatusResponse.class);

            currentplayers =  resp.getPlayers().getOnline();
            maxplayers = resp.getPlayers().getMax();
            motd = resp.getDescription();

            if(motd != null && !motd.equals("")) {
                online = true;
            }

            dataOutputStream.close();
            outputStream.close();
            inputStreamReader.close();
            inputStream.close();
            socket.close();

        }
    }

    public class StatusResponse {
        private Object description;
        private Players players;
        private Version version;
        private String favicon;
        private int time;

        public String getDescription() {
            if(description instanceof LinkedTreeMap) {
                return ((LinkedTreeMap<String,String>) description).get("text");
            }
            return description.toString();
        }


        public Players getPlayers() {
            return players;
        }

        public Version getVersion() {
            return version;
        }

        public String getFavicon() {
            return favicon;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

    }

    public class Players {
        private int max;
        private int online;
        private List<Player> sample;

        public int getMax() {
            return max;
        }

        public int getOnline() {
            return online;
        }

        public List<Player> getSample() {
            return sample;
        }
    }

    public class Player {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

    }

    public class Version {
        private String name;
        private String protocol;

        public String getName() {
            return name;
        }

        public String getProtocol() {
            return protocol;
        }
    }

    public static void pingAllServers() {
        for(ServersController sv : Servers) {
            if(sv.updatePING()) {
                try {
                    sv.ping();
                } catch (IOException e) {
                    sv.online = false;
                    sv.currentplayers = 0;
                    sv.motd = "";
                    //if(Config.getInstance().getDefaultConfig().getBoolean("ShowPingErrorInConsole"))
                    //Bukkit.getLogger().log(Level.INFO, "[BungeeMobs]  Server: "+ sv.getName() +" ("+sv.getIP()+":"+sv.getPort()+")" + " failed to ping respose");
                } catch (ParseException e) {
                    sv.online = false;
                    sv.currentplayers = 0;
                    sv.motd = "";
                    //if(Config.getInstance().getDefaultConfig().getBoolean("ShowPingErrorInConsole"))
                    //Bukkit.getLogger().log(Level.INFO, "[BungeeMobs]  Server: "+ sv.getName() +" ("+sv.getIP()+":"+sv.getPort()+")" + " failed to ping respose");
                }
            }
        }
    }

    public static List<ServersController> getAllServers() {
        return Servers;
    }

    private static int readVarInt(DataInputStream stream) throws IOException {
        int out = 0;
        int bytes = 0;
        byte in;
        while (true) {
            in = stream.readByte();

            out |= (in & 0x7F) << (bytes++ * 7);

            if (bytes > 5) {
                throw new RuntimeException("VarInt too big");
            }

            if ((in & 0x80) != 0x80) {
                break;
            }
        }
        return out;
    }

    private static void writeVarInt(DataOutputStream stream, int value) throws IOException {
        int part;
        while (true) {
            part = value & 0x7F;

            value >>>= 7;
            if (value != 0) {
                part |= 0x80;
            }

            stream.writeByte(part);

            if (value == 0) {
                break;
            }
        }
    }

}
