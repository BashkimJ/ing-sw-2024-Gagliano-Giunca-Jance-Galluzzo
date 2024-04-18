package main.java.it.polimi.ingsw.Model.Player;

import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.GoldCardPlacementException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidPositionException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.InvalidSideException;
import main.java.it.polimi.ingsw.Exceptions.SchemeCardExc.OutOfBoundsException;
import main.java.it.polimi.ingsw.Model.Cards.*;
import main.java.it.polimi.ingsw.Model.Enumerations.Items;
import main.java.it.polimi.ingsw.Model.Enumerations.Pattern;
import main.java.it.polimi.ingsw.Model.Enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;
import static main.java.it.polimi.ingsw.Model.Enumerations.Items.*;
import static main.java.it.polimi.ingsw.Model.Enumerations.Pattern.*;
import static main.java.it.polimi.ingsw.Model.Enumerations.Resource.*;

public class CardScheme {
    private Map<ArrayList<Integer>, Side> playedCards;
    private Map<ArrayList<Integer>,Resource> CardsResource;
    private Map<ArrayList<Integer>,Resource> counted;
    private int [][] Scheme;//0-> Free, 1->Part of the Card,, 2->Free corner, 3->Corner taken or Not visible
    private int numAnimal;
    private  int numInsects;
    private int numFungi;
    private int numPlants;
    private int numInkwell;
    private int numQuill;
    private int numManuscript;

    public CardScheme() {
        numAnimal = 0;
        numInsects = 0;
        numFungi = 0;
        numPlants = 0;
        numInkwell = 0;
        numManuscript = 0;
        numQuill = 0;
        counted = new HashMap<ArrayList<Integer>,Resource>();
        CardsResource = new HashMap<ArrayList<Integer>,Resource>();
        playedCards = new HashMap<ArrayList<Integer>, Side>();
        Scheme = new int[160][160];
    }

    /**
     * Controls if the position is valid.
     * @param x The x coordinate that indicates the number of the array.
     * @param y The y coordinate that indicates the number of the element inside an array.
     * @return True if the position is valid, false otherwise.
     */
    private boolean checkPlacement(int x,int y){
        if(Scheme[x][y] != 0 || Scheme[x][y-1]!=0 || Scheme[x][y+1]!=0 || Scheme[x-1][y]!=0 || Scheme[x+1][y]!=0){
            return false;
        }
        else if(Scheme[x-1][y-1]==3 || Scheme[x+1][y-1]==3 || Scheme[x-1][y+1]==3 || Scheme[x+1][y+1]==3)
        {
            return false;
        }
        return true;
    }

    /**
     * Checks if the position chosen for a card is not exceeding the matrix limits.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if the position is not trying to exceed the limits, false otherwise.
     */
    private boolean checkPosition(int x,int y){
        if(x-1<0 || x+1>159 || y-1<0 || y+1>159){
            return false;
        }
        if(Scheme[x-1][y-1]==0 && Scheme[x-1][y+1]==0 && Scheme[x+1][y-1]==0 && Scheme[x+1][y+1]==0){
            return false;
        }
        return true;
    }

    /**
     * Checks if a gold card can be placed in a certain momemnt of the game.
     * @param card The gold card to be placed.
     * @return True if the card can be placed false othersie.
     */
    private boolean checkPlacementCondition(GoldCard card){
        int animal = 0;
        int insects = 0;
        int plant = 0;
        int fungi = 0;
        List<Resource> rsc = new ArrayList<Resource>(card.getNeccesaryRes());
        for(Resource r:rsc){
            if(r==Animal){
                animal++;
            }
            else if(r==Fungi){
                fungi++;
            }
            else if(r==Plant){
                plant++;
            }
            else if(r==Insects){
                insects++;
            }
        }
        if(animal>numAnimal || insects>numInsects || plant>numPlants || fungi>numFungi){
            return false;
        }
        return true;
    }

    /**
     * Counts the resource of the card.
     * @param typeTocount The resource to count.
     * @param sideToCount The side to count.
     * @param playedCard The card that is going to be placed in the game.
     * @return The number of the resources counted.
     */
    private int countResources(Resource typeTocount, Side sideToCount, Card playedCard){
        int numRsc = 0;
        List<Resource> InitialRsc;
        if(playedCard instanceof InitialCard){
            if(sideToCount.equals(playedCard.getFront())){
                InitialRsc = new ArrayList<Resource>(((InitialCard) playedCard).getMiddleResource());
                for(int i=0;i<InitialRsc.size();i++){
                    if(InitialRsc.get(i).equals(typeTocount)){
                        numRsc++;
                    }
                }
                if(sideToCount.getUpLeft().getResource()==typeTocount){
                    numRsc++;
                }
                if(sideToCount.getUpRight().getResource()==typeTocount){
                    numRsc++;
                }
                if(sideToCount.getDownLeft().getResource()==typeTocount){
                    numRsc++;
                }
                if(sideToCount.getDownRight().getResource()==typeTocount){
                    numRsc++;
                }

            }
            if(sideToCount.equals(playedCard.getRetro())){
                if(sideToCount.getUpLeft().getResource()==typeTocount){
                    numRsc++;
                }
                if(sideToCount.getUpRight().getResource()==typeTocount){
                    numRsc++;
                }
                if(sideToCount.getDownLeft().getResource()==typeTocount){
                    numRsc++;
                }
                if(sideToCount.getDownRight().getResource()==typeTocount){
                    numRsc++;
                }


            }
        }
        else{
            if(sideToCount.equals(playedCard.getRetro())) {
                if (((ResourceCard) playedCard).getResourceType()==typeTocount) {
                    numRsc++;
                }
            }
            if(sideToCount.equals(playedCard.getFront())){
                if(sideToCount.getDownRight().getResource()== typeTocount){
                    numRsc++;
                }
                if(sideToCount.getUpRight().getResource()==typeTocount){
                    numRsc++;
                }
                if(sideToCount.getDownLeft().getResource()==typeTocount){
                    numRsc++;
                }
                if(sideToCount.getUpLeft().getResource()==typeTocount){
                    numRsc++;
                }

            }
        }
        return numRsc;
    }

    /**
     * Count the items in a certain moment of the game.
     * @param typeToCount The item type to be counted.
     * @param sideTocount The side to count.
     * @param playedCard The card to count.
     * @return The number of items counted.
     */
    private int countObjects(Object typeToCount,Side sideTocount, Card playedCard){
        int numObjects = 0;
        if(sideTocount.equals(playedCard.getRetro())){
            return 0;
        }
        else {

            if (sideTocount.getDownRight().getItems() == typeToCount) {
                numObjects++;
            } else if (sideTocount.getDownLeft().getItems() == typeToCount) {
                numObjects++;
            } else if (sideTocount.getUpLeft().getItems() == typeToCount) {
                numObjects++;
            } else if (sideTocount.getUpRight().getItems() == typeToCount) {
                numObjects++;
            }
        }
        return numObjects;
    }

    /**
     * Updates the number of the resources when the corner passed as parameter is covered by another card.
     * @param check  The corner to consider.
     */
    private void updateObjectsResources(Corner check){
        if(check.getResource()==Animal){
        numAnimal--;
        }
        if(check.getResource()==Fungi){
            numFungi--;
        }
        if(check.getResource()==Insects){
            numInsects--;
        }
        if(check.getResource()==Plant){
            numPlants--;
        }
        if(check.getItems()==Inkwell){
            numInkwell--;
        }
        if(check.getItems()==Manuscript){
            numManuscript--;
        }
        if(check.getItems()==Quill){
            numQuill--;
        }

    }

    /**
     * Finds the corner that is going to be covered by a card placement.
     * @param currentPosition The position (x,y) of the new placed card.
     * @param cornerToCheck The corner of the old card getting covered, passed as string.
     */
    private void checkCorner(int[] currentPosition, String cornerToCheck){
        int x = currentPosition[0];
        int y = currentPosition[1];
        Corner check = null;
        ArrayList<Integer> CardPositionToUpdate = new ArrayList<Integer>();
        if(cornerToCheck.equalsIgnoreCase("downRight")){
            CardPositionToUpdate.add(0,x-2);
            CardPositionToUpdate.add(1,y-2);
            check = playedCards.get(CardPositionToUpdate).getDownRight();
            updateObjectsResources(check);
        }
        if(cornerToCheck.equalsIgnoreCase("upRight")){
            CardPositionToUpdate.add(0,x+2);
            CardPositionToUpdate.add(1,y-2);
            check = playedCards.get(CardPositionToUpdate).getUpRight();
            updateObjectsResources(check);
        }
        if(cornerToCheck.equalsIgnoreCase("downLeft")){
            CardPositionToUpdate.add(0,x-2);
            CardPositionToUpdate.add(1, y+2);
            check = playedCards.get(CardPositionToUpdate).getDownLeft();
            updateObjectsResources(check);
        }
        if(cornerToCheck.equalsIgnoreCase("upLeft")){
            CardPositionToUpdate.add(0, x+2);
            CardPositionToUpdate.add(1,y+2);
            check = playedCards.get(CardPositionToUpdate).getUpLeft();
            updateObjectsResources(check);
        }

    }


    /**
     * The method that places the initial card of the game. The position of the initial card is predefined, and it is (40,40).
     * @param initial The initial card to be played.
     * @param side The side chosen to place the card.The string side must correspond with the words front or retro to choose the side.
     * @return True after the card is placed.
     * @throws InvalidSideException When an invalid side is chosen.
     **/
   public boolean placeInitialCard(InitialCard initial,String side)throws InvalidSideException{
        Side playedSide ;
        if(side.equalsIgnoreCase("front")){
            playedSide = initial.getFront();
        }
        else if(side.equalsIgnoreCase("retro")){
            playedSide = initial.getRetro();
        }
        else{
            throw new InvalidSideException();
        }
       numAnimal = numAnimal + countResources(Animal,playedSide,initial);
       numFungi = numFungi + countResources(Fungi,playedSide,initial);
       numPlants = numPlants + countResources(Plant,playedSide,initial);
       numInsects = numInsects + countResources(Insects,playedSide,initial);
       Scheme[80][80] = 1;
       Scheme[80][79] = 1;
       Scheme[80][81] = 1;
       Scheme[79][80] = 1;
       Scheme[81][80] = 1;
       if(playedSide.getUpLeft().isVisible()){
           Scheme[79][79] = 2;
       }else Scheme[79][79] = 3;
       if(playedSide.getDownLeft().isVisible()){
           Scheme[81][79] = 2;
       }else Scheme[81][79] = 3;
       if(playedSide.getUpRight().isVisible()){
           Scheme[79][81] = 2;
       }else Scheme[79][81] = 3;
       if(playedSide.getDownRight().isVisible()){
           Scheme[81][81] = 2;
       }else Scheme[81][81] = 3;
       ArrayList position = new ArrayList();
       position.add(0,80);
       position.add(1,80);
       playedCards.put(position,playedSide);
       return true;

   }

    /**
     * This method allows to play a card gold or resource.
     * @param resource The card to be played.
     * @param positionTobePlaced The position of the card interms of x and y.
     * @param side The side chosen to be played
     * @return The points the card gives when played.
     * @throws GoldCardPlacementException When there are not enough resources or items to play the card.
     * @throws OutOfBoundsException When the position chosen tries to exceed the limit of the scheme.
     * @throws InvalidPositionException When the card cant be placed in the position chosen because it is covering a not visible corner or is overlapping another card.
     * @throws InvalidSideException The side chosen is not valid.
     */
   public int placeCard(ResourceCard resource, int[] positionTobePlaced,String side)throws GoldCardPlacementException, OutOfBoundsException, InvalidPositionException, InvalidSideException {
       if(resource instanceof GoldCard){
           if(!checkPlacementCondition((GoldCard) resource)) throw new GoldCardPlacementException();
       }
       int x = positionTobePlaced[0];
       int y = positionTobePlaced[1];
       if(!checkPosition(x,y)){
           throw new OutOfBoundsException();
       }
       if(!checkPlacement(x,y)){
           throw new InvalidPositionException();
       }
       Side sideToBeplaced = null;
       if(side.equalsIgnoreCase("front")){
           sideToBeplaced = resource.getFront();
       }
       else if(side.equalsIgnoreCase("retro")){
           sideToBeplaced = resource.getRetro();
       }
       else{
           throw new InvalidSideException();
       }
       int corners_covering = 0;
       numAnimal  = numAnimal + countResources(Animal,sideToBeplaced,resource);
       numFungi  = numFungi + countResources(Fungi,sideToBeplaced,resource);
       numPlants  = numPlants + countResources(Plant,sideToBeplaced,resource);
       numInsects  = numInsects + countResources(Insects,sideToBeplaced,resource);
       numInkwell = numInkwell + countObjects(Inkwell,sideToBeplaced,resource);
       numManuscript = numManuscript + countObjects(Manuscript,sideToBeplaced,resource);
       numQuill = numQuill + countObjects(Quill,sideToBeplaced,resource);
       //Update Scheme with the proper values
       Scheme[x][y] = 1;
       Scheme[x+1][y] = 1;
       Scheme[x-1][y] = 1;
       Scheme[x][y-1] = 1;
       Scheme[x][y+1] = 1;
       //Update the upLeft corner
       if(Scheme[x-1][y-1]==0){
           if(sideToBeplaced.getUpLeft().isVisible()) Scheme[x-1][y-1] = 2;
           if(!sideToBeplaced.getUpLeft().isVisible()) Scheme[x-1][y-1] = 3;
       }
       else if(Scheme[x-1][y-1]==2) {
           Scheme[x - 1][y - 1] = 3;
           corners_covering++;
           checkCorner(positionTobePlaced,"downRight");

       }
       //Update the downLeft corner
       if(Scheme[x+1][y-1]==0){
           if(sideToBeplaced.getDownLeft().isVisible()) Scheme[x+1][y-1] = 2;
           if(!sideToBeplaced.getDownLeft().isVisible())Scheme[x+1][y-1] = 3;
       }
       else if(Scheme[x+1][y-1]==2) {
           Scheme[x + 1][y - 1] = 3;
           corners_covering++;
           checkCorner(positionTobePlaced,"upRight");
       }
       //Update the upRight corner
       if(Scheme[x-1][y+1]==0){
           if(sideToBeplaced.getUpRight().isVisible()) Scheme[x-1][y+1] = 2;
           if(!sideToBeplaced.getUpRight().isVisible())Scheme[x-1][y+1] = 3;
       }
       else if(Scheme[x-1][y+1]==2) {
           Scheme[x - 1][y + 1] = 3;
           corners_covering++;
           checkCorner(positionTobePlaced,"downLeft");
       }
       //update the downright corner
       if(Scheme[x+1][y+1]==0){
           if(sideToBeplaced.getDownRight().isVisible()) Scheme[x+1][y+1] = 2;
           if(!sideToBeplaced.getDownRight().isVisible())Scheme[x+1][y+1] = 3;
       }
       else if(Scheme[x+1][y+1]==2) {
           Scheme[x + 1][y + 1] = 3;
           corners_covering++;
           checkCorner(positionTobePlaced,"upLeft");
       }
       ArrayList<Integer> pos = new ArrayList<Integer>();
       pos.add(0,positionTobePlaced[0]);
       pos.add(1,positionTobePlaced[1]);
       playedCards.put(pos,sideToBeplaced);
       CardsResource.put(pos,resource.getResourceType());
       if(resource instanceof GoldCard && ((GoldCard) resource).getCondition()!=null){
           if(((GoldCard) resource).getCondition().getCornerCondition()==true){
               return corners_covering*(resource.getPoints());
           }
           else{
               if(((GoldCard) resource).getCondition().getItemType()==Inkwell)return numInkwell*resource.getPoints();
               else if (((GoldCard) resource).getCondition().getItemType()==Quill) return numQuill*resource.getPoints();
               else if (((GoldCard) resource).getCondition().getItemType()==Manuscript) return numManuscript*resource.getPoints();
           }
       }
       return resource.getPoints();
   }

   public int ControlObjective(ObjectiveCard objective){
       int min = 0;
       int pattern_found = 0;
       ArrayList<Integer> firstmove= new ArrayList<Integer>();
       ArrayList<Integer> secondmove= new ArrayList<Integer>();
       if (objective.getObjective() instanceof ObjectiveWithItems){
           if(((ObjectiveWithItems)(objective.getObjective())).getObjectives().size()==3)
           {
               min = min(numManuscript,numInkwell);
               min= min(min,numQuill);
               return min*3;
           }
           else{
               if(((ObjectiveWithItems)(objective.getObjective())).getObjectives().get(0)==Inkwell)
                   return 2*(numInkwell/2);
               if(((ObjectiveWithItems)(objective.getObjective())).getObjectives().get(0)==Manuscript)
                   return 2*(numManuscript/2);
               if(((ObjectiveWithItems)(objective.getObjective())).getObjectives().get(0)==Quill)
                   return 2*(numQuill/2);
           }
       }
       else if(objective.getObjective() instanceof ObjectiveWithResources){
           if(((ObjectiveWithResources)(objective.getObjective())).getObjectives().get(0)==Animal)
               return 2*(numAnimal/3);
           if(((ObjectiveWithResources)(objective.getObjective())).getObjectives().get(0)==Insects)
               return 2*(numInsects/3);
           if(((ObjectiveWithResources)(objective.getObjective())).getObjectives().get(0)==Plant)
               return 2*(numPlants/3);
           if(((ObjectiveWithResources)(objective.getObjective())).getObjectives().get(0)==Fungi)
               return 2*(numFungi/3);
       }
       else {
           Pattern toControl = ((ObjectiveWithPattern)(objective.getObjective())).getObjectives();
           if(toControl == downUpLR_RED){
               for(ArrayList<Integer> position : CardsResource.keySet()){
                   if(!counted.containsKey(position) && CardsResource.get(position)==Fungi){
                       firstmove.add(0,position.get(0)-2);
                       firstmove.add(1,position.get(1)+2);
                       if(!counted.containsKey(firstmove) && CardsResource.get(firstmove)==Fungi){
                          secondmove.add(0,firstmove.get(0)-2);
                          secondmove.add(1,firstmove.get(1)+2);
                          if(!counted.containsKey(secondmove) && CardsResource.get(secondmove)==Fungi){
                              counted.put(secondmove,Fungi);
                              counted.put(position,Fungi);
                              counted.put(firstmove,Fungi);
                              pattern_found++;

                          }
                       }

                   }
               }
               return pattern_found*2;
           }
           else if(toControl == upDownLR_GREEN){
               for(ArrayList<Integer> position : CardsResource.keySet()){
                   if(!counted.containsKey(position) && CardsResource.get(position)==Plant){
                       firstmove.add(0,position.get(0)+2);
                       firstmove.add(1,position.get(1)+2);
                       if(!counted.containsKey(firstmove) && CardsResource.get(firstmove)==Plant){
                           secondmove.add(0,firstmove.get(0)+2);
                           secondmove.add(1,firstmove.get(1)+2);
                           if(!counted.containsKey(secondmove) && CardsResource.get(secondmove)==Plant){
                               counted.put(secondmove,Plant);
                               counted.put(position,Plant);
                               counted.put(firstmove,Plant);
                               pattern_found++;

                           }
                       }

                   }
               }
               return pattern_found*2;

           }
           else if(toControl == downUpLR_PURPLE){
               for(ArrayList<Integer> position : CardsResource.keySet()){
                   if(!counted.containsKey(position) && CardsResource.get(position)==Insects){
                       firstmove.add(0,position.get(0)-2);
                       firstmove.add(1,position.get(1)+2);
                       if(!counted.containsKey(firstmove) && CardsResource.get(firstmove)==Insects){
                           secondmove.add(0,firstmove.get(0)-2);
                           secondmove.add(1,firstmove.get(1)+2);
                           if(!counted.containsKey(secondmove) && CardsResource.get(secondmove)==Insects){
                               counted.put(secondmove,Insects);
                               pattern_found++;
                               counted.put(position,Insects);
                               counted.put(firstmove,Insects);
                           }
                       }

                   }
               }
               return pattern_found*2;
           }
           else if(toControl == upDownLR_BLUE){
               for(ArrayList<Integer> position : CardsResource.keySet()){
                   if(!counted.containsKey(position) && CardsResource.get(position)==Animal){
                       firstmove.add(0,position.get(0)+2);
                       firstmove.add(1,position.get(1)+2);
                       if(!counted.containsKey(firstmove) && CardsResource.get(firstmove)==Animal){
                           secondmove.add(0,firstmove.get(0)+2);
                           secondmove.add(1,firstmove.get(1)+2);
                           if(!counted.containsKey(secondmove) && CardsResource.get(secondmove)==Animal){
                               counted.put(secondmove,Animal);
                               counted.put(position,Animal);
                               counted.put(firstmove,Animal);
                               pattern_found++;

                           }
                       }

                   }
               }
               return pattern_found*2;

           }
           else if(toControl == LdownLeft){
               for(ArrayList<Integer> position : CardsResource.keySet()){
                   if(!counted.containsKey(position) && CardsResource.get(position)==Plant){
                       firstmove.add(0,position.get(0)+4);
                       firstmove.add(1,position.get(1));
                       if(!counted.containsKey(firstmove) && CardsResource.get(firstmove)==Plant){
                           secondmove.add(0,firstmove.get(0)+2);
                           secondmove.add(1,firstmove.get(1)-2);
                           if(!counted.containsKey(secondmove) && CardsResource.get(secondmove)==Insects){
                               counted.put(secondmove,Animal);
                               counted.put(firstmove,Plant);
                               counted.put(position,Plant);
                               pattern_found++;
                           }
                       }

                   }
               }
               return pattern_found*3;

           }
           else if(toControl==LupLeft){
               for(ArrayList<Integer> position : CardsResource.keySet()){
                   if(!counted.containsKey(position) && CardsResource.get(position)==Insects){
                       firstmove.add(0,position.get(0)-4);
                       firstmove.add(1,position.get(1));
                       if(!counted.containsKey(firstmove) && CardsResource.get(firstmove)==Insects){
                           secondmove.add(0,firstmove.get(0)-2);
                           secondmove.add(1,firstmove.get(1)-2);
                           if(!counted.containsKey(secondmove) && CardsResource.get(secondmove)==Animal){
                               counted.put(secondmove,Animal);
                               counted.put(position,Insects);
                               counted.put(firstmove,Insects);
                               pattern_found++;
                           }
                       }

                   }
               }
               return pattern_found*3;

           }
           else if(toControl == LupRight){
               for(ArrayList<Integer> position : CardsResource.keySet()){
                   if(!counted.containsKey(position) && CardsResource.get(position)==Animal){
                       firstmove.add(0,position.get(0)-4);
                       firstmove.add(1,position.get(1));
                       if(!counted.containsKey(firstmove) && CardsResource.get(firstmove)==Animal){
                           secondmove.add(0,firstmove.get(0)-2);
                           secondmove.add(1,firstmove.get(1)+2);
                           if(!counted.containsKey(secondmove) && CardsResource.get(secondmove)==Fungi){
                               counted.put(secondmove,Fungi);
                               counted.put(position,Animal);
                               counted.put(firstmove,Animal);
                               pattern_found++;

                           }
                       }

                   }
               }
               return pattern_found*3;

           }
           else {
               for(ArrayList<Integer> position : CardsResource.keySet()){
                   if(!counted.containsKey(position) && CardsResource.get(position)==Fungi){
                       firstmove.add(0,position.get(0)+4);
                       firstmove.add(1,position.get(1));
                       if(!counted.containsKey(firstmove) && CardsResource.get(firstmove)==Fungi){
                           secondmove.add(0,firstmove.get(0)+2);
                           secondmove.add(1,firstmove.get(1)+2);
                           if(!counted.containsKey(secondmove) && CardsResource.get(secondmove)==Plant){
                               counted.put(secondmove,Plant);
                               counted.put(position,Fungi);
                               counted.put(firstmove,Fungi);
                               pattern_found++;

                           }
                       }

                   }
               }
               return pattern_found*3;

           }

       }
       return pattern_found;
   }



    /**
     *
     * @param type The type of the resource for which we need the number already present in the game.
     * @return The number of the resource of the type: type.
     */
   public int getResourceNum(Resource type){
       if(type==Animal)return numAnimal;
       else if(type==Insects) return numInsects;
       else if(type==Plant) return numPlants;
       else if(type==Fungi) return numFungi;
       return 0;
   }

    /**
     *
     * @param type The item type for which we need the number already present in the game.
     * @return The number of the item: type.
     */
   public int getItemNum(Items type){
        if(type==Inkwell)return numInkwell;
        else if(type==Quill) return numQuill;
        else if(type==Manuscript) return numManuscript;
        return 0;
   }

    /**
     *
     * @return The matrix of the scheme.
     */
   public int[][] show() {
       return this.Scheme;
   }
    /**
     *
     * @return The map of the position of each played side.
     */
   public Map<ArrayList<Integer>,Side> getPlayedCards(){
        return this.playedCards;
   }

}
