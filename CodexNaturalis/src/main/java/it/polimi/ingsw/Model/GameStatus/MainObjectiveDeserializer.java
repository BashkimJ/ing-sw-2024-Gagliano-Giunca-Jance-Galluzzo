package main.java.it.polimi.ingsw.Model.GameStatus;

import com.google.gson.*;
import main.java.it.polimi.ingsw.Model.Cards.MainObjective;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveWithItems;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveWithPattern;
import main.java.it.polimi.ingsw.Model.Cards.ObjectiveWithResources;

import java.lang.reflect.Type;

public class MainObjectiveDeserializer implements JsonDeserializer<MainObjective> {

    @Override
    public MainObjective deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //returns ObjectiveWithPattern instance
        if(jsonObject.has("patternType")){
            return context.deserialize(jsonObject, ObjectiveWithPattern.class);
        }
        //returns ObjectiveWithItems instance
        if (jsonObject.has("objectivesITEMS")) {
            return context.deserialize(jsonObject, ObjectiveWithItems.class);
        }
        //returns ObjectiveWithResources instance
        return context.deserialize(jsonObject, ObjectiveWithResources.class);

    }
}
