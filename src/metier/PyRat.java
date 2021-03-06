package metier;

import outils.Labyrinthe;

import java.util.*;

public class PyRat {

    //ArrayList<Point> lstFromages;
    private HashSet<Point> lstFromagesn1;
    private HashSet<Point> passagePossiblen1;
    private HashMap<Point, HashSet<Point>> mapN1;
    /* Méthode appelée une seule fois permettant d'effectuer des traitements "lourds" afin d'augmenter la performace de la méthode turn. */
    public void preprocessing(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        /*lstFromages = new ArrayList<>();
        for(int i=0; i<fromages.size(); i++){
            lstFromages.add(fromages.get(i));
        }*/
        lstFromagesn1 = new HashSet<>();
        lstFromagesn1.addAll(fromages);

        mapN1 = new HashMap<>();
        passagePossiblen1 = new HashSet<>();
        for(Point point : laby.keySet()){
           for(Point point2 : laby.get(point)){
               passagePossiblen1.add(point2);
           }
           mapN1.put(point, passagePossiblen1);
        }
    }

    /* Méthode de test appelant les différentes fonctionnalités à développer.
        @param laby - Map<Point, List<Point>> contenant tout le labyrinthe, c'est-à-dire la liste des Points, et les Points en relation (passages existants)
        @param labyWidth, labyHeight - largeur et hauteur du labyrinthe
        @param position - Point contenant la position actuelle du joueur
        @param fromages - List<Point> contenant la liste de tous les Points contenant un fromage. */
    public void turn(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point pt1 = new Point(1,4);
        Point pt2 = new Point(3,1);
        System.out.println((fromageIci(pt1, fromages) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt1);
        System.out.println((fromageIci_EnOrdreConstant(pt2) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt2);
        System.out.println((passagePossible(pt1, pt2, laby) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println("Liste des points inatteignables depuis la position " + position + " : " + pointsInatteignables(position, laby));
    }

    /* Regarde dans la liste des fromages s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci(Point pos, List<Point> fromages) {
        for(Point point : fromages){
            if(point.equals(pos)){
                return true;
            }
        }
        return false;
    }

    /* Regarde de manière performante (accès en ordre constant) s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci_EnOrdreConstant(Point pos) {
        if(lstFromagesn1.contains(pos)){
            return true;
        } else {
            return false;
        }
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a ».
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible(Point de, Point a, Map<Point, List<Point>> laby) {
        for(int i=0; i<laby.size(); i++){
            if(laby.containsKey(de)){
                if(laby.get(de).contains(a)){
                    return true;
                }
            }
        }
        return false;
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a »,
        mais sans devoir parcourir la liste des Points se trouvant dans la Map !
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible_EnOrdreConstant(Point de, Point a) {
        if (mapN1.containsKey(de)) {
            if(mapN1.get(de).contains(a)){
                return true;
            }
        }
        return false;
    }

    /* Retourne la liste des points qui ne peuvent pas être atteints depuis la position « pos ».
        @return la liste des points qui ne peuvent pas être atteints depuis la position « pos ». */
    private List<Point> pointsInatteignables(Point pos, Map<Point, List<Point>> laby) {
        List<Point> pointsIna = laby.get(pos);
        ArrayList<Point> lstPoints = new ArrayList(laby.entrySet());
        for(Point point : pointsIna){
            lstPoints.remove(point);
        }
     return lstPoints;
    }
}