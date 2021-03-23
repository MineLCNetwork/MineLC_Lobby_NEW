package com.minelc.LOBBY.Commands;

import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.CORE.Controller.Ranks;
import com.minelc.CORE.Utils.Util;
import com.minelc.LOBBY.LobbyMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RankSubCMD implements CommandInterface {
    public RankSubCMD() {
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, final String[] args) {
        try {
            Bukkit.getScheduler().runTaskAsynchronously(LobbyMain.getInstance(), new Runnable() {
                public void run() {
                    CommandSender p = sender;
                    Jugador j = null;
                    String playername;
                    if (args.length == 5) {
                        playername = args[4];
                        String rango = args[2].toUpperCase();
                        long duracion = Long.parseLong(args[3]);
                        j = Jugador.getJugador(playername);
                        if (args[1].equalsIgnoreCase("addrank")) {
                            if (j.getBukkitPlayer() == null) {
                                Database.loadPlayerRank_SYNC(j);
                            }

                            Util.logToFile("-----------------------------------------", LobbyMain.getInstance(), "vip.log");
                            Util.logToFile("El usuario " + playername + " con el rango " + j.getRank().name() + " de duracion: " + j.getRankduration() + " intenta comprar el rango" + rango + " de " + duracion, LobbyMain.getInstance(), "vip.log");
                            Ranks ranguito = Ranks.valueOf(rango);

                            switch (j.getRank()) { // SWITCHING CASE WHEN PLAYER HAVE RANK
                                case RUBY:
                                    if (ranguito == Ranks.RUBY) {
                                        j.addRankduration(duracion);
                                        Database.savePlayerRankSYNC(j);
                                        Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                    } else {
                                        Util.logToFile("El usuario " + playername + " tiene rango ruby y se le ha intentado agregar " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                    }
                                    break;
                                case ELITE:
                                    switch (ranguito) { // SWITCHING CASE TYPE OF RANK --- 1
                                        case RUBY:
                                            j.setRank(ranguito);
                                            j.setRankduration(duracion);
                                            Database.savePlayerRankSYNC(j);
                                            Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                            break;
                                        case ELITE:
                                            j.addRankduration(duracion);
                                            Database.savePlayerRankSYNC(j);
                                            Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                            break;
                                        default:
                                            Util.logToFile("El usuario " + playername + " tiene rango " + j.getRank().toString() + " y se le ha intentado agregar " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                            break;
                                    }
                                    break;
                                case SVIP:
                                    switch (ranguito) { // SWITCHING CASE TYPE OF RANK --- 1
                                        case ELITE:
                                        case RUBY:
                                            j.setRank(ranguito);
                                            j.setRankduration(duracion);
                                            Database.savePlayerRankSYNC(j);
                                            Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                            break;
                                        case SVIP:
                                            j.addRankduration(duracion);
                                            Database.savePlayerRankSYNC(j);
                                            Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                            break;
                                        default:
                                            Util.logToFile("El usuario " + playername + " tiene rango " + j.getRank().toString() + " y se le ha intentado agregar " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                            break;
                                    }
                                    break;
                                case VIP:
                                    switch (ranguito) { // SWITCHING CASE TYPE OF RANK --- 1
                                        case SVIP:
                                        case ELITE:
                                        case RUBY:
                                            j.setRank(ranguito);
                                            j.setRankduration(duracion);
                                            Database.savePlayerRankSYNC(j);
                                            Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                            break;
                                        case VIP:
                                            j.addRankduration(duracion);
                                            Database.savePlayerRankSYNC(j);
                                            Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                            break;
                                        default:
                                            Util.logToFile("El usuario " + playername + " tiene rango " + j.getRank().toString() + " y se le ha intentado agregar " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                            break;
                                    }
                                    break;
                                case DEFAULT:
                                case PREMIUM:
                                    j.setRank(ranguito);
                                    j.setRankduration(duracion);
                                    Database.savePlayerRankSYNC(j);
                                    Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                    break;
                                default:
                                    Util.logToFile("El usuario " + playername + " tiene rango " + j.getRank().toString() + " y se le ha intentado agregar " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                            }
                            /*
                            if (j.is_RUBY()) {
                                if (rango.equalsIgnoreCase(Ranks.RUBY.name())) {
                                    j.addRankduration(duracion);
                                    Database.savePlayerRankSYNC(j);
                                    Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                } else {
                                    Util.logToFile("El usuario " + playername + " tiene rago ruby y se le ha intentado agregar " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                }
                            } else if (j.is_ELITE()) {
                                if (rango.equalsIgnoreCase(Ranks.ELITE.name())) {
                                    j.addRankduration(duracion);
                                    Database.savePlayerRankSYNC(j);
                                    Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                } else if (rango.equalsIgnoreCase(Ranks.RUBY.name())) {
                                    j.addRankduration(duracion);
                                    Database.savePlayerRankSYNC(j);
                                    Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                } else {
                                    Util.logToFile("El usuario " + playername + " tiene rago elite y se le ha intentado agregar " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                }
                            } else if (j.is_SVIP()) {
                                if (rango.equalsIgnoreCase(Ranks.SVIP.name())) {
                                    j.addRankduration(duracion);
                                    Database.savePlayerRankSYNC(j);
                                    Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                } else if (rango.equalsIgnoreCase(Ranks.ELITE.name())) {
                                    j.setRank(Ranks.ELITE);
                                    j.setRankduration(duracion);
                                    Database.savePlayerRankSYNC(j);
                                    Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                } else {
                                    Util.logToFile("El usuario " + playername + " tiene rago svip y se le ha intentado agregar " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                }
                            } else if (j.is_VIP()) {
                                if (rango.equalsIgnoreCase(Ranks.VIP.name())) {
                                    j.addRankduration(duracion);
                                    Database.savePlayerRankSYNC(j);
                                    Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                } else if (rango.equalsIgnoreCase(Ranks.SVIP.name())) {
                                    j.setRank(Ranks.SVIP);
                                    j.setRankduration(duracion);
                                    Database.savePlayerRankSYNC(j);
                                    Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                } else if (rango.equalsIgnoreCase(Ranks.ELITE.name())) {
                                    j.setRank(Ranks.ELITE);
                                    j.setRankduration(duracion);
                                    Database.savePlayerRankSYNC(j);
                                    Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                } else {
                                    Util.logToFile("El usuario " + playername + " tiene rago vip y se le ha intentado agregar " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                                }
                            } else if (j.getRank() == Ranks.DEFAULT || j.getRank() == Ranks.PREMIUM) { // Ahora revisa si es premium o DEFAULT.
                                j.setRank(Ranks.valueOf(rango));
                                j.setRankduration(duracion);
                                Database.savePlayerRankSYNC(j);
                                Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                            }

                            if (j.getBukkitPlayer() == null) {
                                Jugador.removeJugador(j.getPlayerName());
                            } */
                        } else if (args[1].equalsIgnoreCase("setrank")) {
                            if (j.getBukkitPlayer() == null) {
                                Database.loadPlayerRank_SYNC(j);
                            }

                            j.setRank(Ranks.valueOf(rango));
                            j.setRankduration(duracion);
                            Database.savePlayerRankSYNC(j);
                            Util.logToFile("El usuario " + playername + " compro el rango " + rango + " de duracion: " + duracion, LobbyMain.getInstance(), "vip.log");
                            if (j.getBukkitPlayer() == null) {
                                Jugador.removeJugador(j.getPlayerName());
                            }
                        }
                    } else if (args.length == 4) {
                        playername = args[3];
                        int vippoints = Integer.parseInt(args[2]);
                        j = Jugador.getJugador(playername);
                        if (args[1].equalsIgnoreCase("addvippoints")) {
                            if (j.getBukkitPlayer() == null) {
                                Database.loadPlayerRank_SYNC(j);
                            }

                            Util.logToFile("-----------------------------------------", LobbyMain.getInstance(), "vip.log");
                            Util.logToFile("El usuario " + playername + " con " + j.getRankpoints() + " vippoints intenta comprar " + vippoints, LobbyMain.getInstance(), "vip.log");
                            j.addRankpoints(vippoints);
                            Database.savePlayerRankSYNC(j);
                            Util.logToFile("El usuario " + playername + " compro " + vippoints + " vippoints", LobbyMain.getInstance(), "vip.log");
                        } else if (args[1].equalsIgnoreCase("removevippoints")) {
                            if (j.getBukkitPlayer() == null) {
                                Database.loadPlayerRank_SYNC(j);
                            }

                            Util.logToFile("-----------------------------------------", LobbyMain.getInstance(), "vip.log");
                            Util.logToFile("El usuario " + playername + " con " + j.getRankpoints() + " vippoints intenta comprar " + vippoints, LobbyMain.getInstance(), "vip.log");
                            j.removeRankpoints(vippoints);
                            Database.savePlayerRankSYNC(j);
                            Util.logToFile("El usuario " + playername + " compro " + vippoints + " vippoints", LobbyMain.getInstance(), "vip.log");
                        } else if (args[1].equalsIgnoreCase("setvippoints")) {
                            if (j.getBukkitPlayer() == null) {
                                Database.loadPlayerRank_SYNC(j);
                            }

                            Util.logToFile("-----------------------------------------", LobbyMain.getInstance(), "vip.log");
                            Util.logToFile("El usuario " + playername + " con " + j.getRankpoints() + " vippoints intenta comprar " + vippoints, LobbyMain.getInstance(), "vip.log");
                            j.setRankpoints(vippoints);
                            Database.savePlayerRankSYNC(j);
                            Util.logToFile("El usuario " + playername + " compro " + vippoints + " vippoints", LobbyMain.getInstance(), "vip.log");
                        }

                        if (j.getBukkitPlayer() == null) {
                            Jugador.removeJugador(j.getPlayerName());
                        }
                    } else {
                        p.sendMessage("");
                        p.sendMessage(ChatColor.GREEN + "Comandos de rango");
                        p.sendMessage(ChatColor.YELLOW + "/minelc rank <addrank, setrank> <rango> <duracion> <usuario>");
                        p.sendMessage(ChatColor.YELLOW + "/minelc rank <addvippoints, setvippoints, removevippoints> <cantidad> <usuario>");
                    }

                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
            Util.logToFile("Fallo al intentar agregar VIP, datos: " + args.toString(), LobbyMain.getInstance(), "vip.log");
        }

        return true;
    }
}
