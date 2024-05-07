package main.java.it.polimi.ingsw.Network.Messages;

public enum MessageType {
    //Lobby State
    Login_Req,
    Login_Rpl,
    Error_Message,
    Player_Num_Req,
    Player_Num_Resp,

    //In_Game state
    Choose_Obj_Req,
    Choose_Obj_Res,
    Initial_Card_Mess,
    Place_Initial_Card,
    Init_Cl,
    Chat_Mess,
    Player_Info,
    Place_Card,
    Game_St,
    Pick_Card,
    Winner_Mess,



}
