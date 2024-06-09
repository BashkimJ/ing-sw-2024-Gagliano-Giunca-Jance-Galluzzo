package it.polimi.ingsw.Network.Messages;

/**
 * Enumeration containing all the types of messages.
 */
public enum MessageType {
    //Lobby State
    New_game,
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
    Game_Started,
    Chat_Mess,
    Player_Info,
    Place_Card,
    Game_St,
    Pick_Card,
    Winner_Mess,
    Ping,
    Player_Move_Resp,



}
