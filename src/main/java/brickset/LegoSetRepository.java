package brickset;

import repository.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }


    /**
     * Returns the number of LEGO sets with the tag specified.
     *
     * @param tag a LEGO set tag
     * @return the number of LEGO sets with the tag specified
     */
    public long countLegoSetsWithTag(String tag) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null && legoSet.getTags().contains(tag))
                .count();
    }

    /**
     * Megvizsgálja hogy van-e egyforma számmal és gyártási évvel rendelkező Lego szett
     * @return Megvizsgálja hogy van-e egyforma számmal és gyártási évvel rendelkező Lego szett és visszaad egy true vagy egy false-t
     */

    public boolean checkIfWeHaveLegoSetNumberAndYearEqual(){

        return getAll().stream().anyMatch(legoSet -> Integer.parseInt(legoSet.getNumber()) == legoSet.getYear().getValue());
    }

    /**
     *  Kiírja a Lego szett első 2 betűjét
     */
    public void printLegoSetsFirstTwoLetter(){
        getAll().stream().flatMap(legoSet -> Stream.of(legoSet.getName().substring(0,2))).forEach(System.out::println);
    }

    /**
     * Kiírja a leghosszabb nevű Lego szett nevének az adatait
     * @return
     */

    public LegoSet printLongestLegoSetNameData(){
         LegoSet longestString=getAll().stream().reduce((legoSetName1, legoSetName2) -> legoSetName1.getName().length() > legoSetName2.getName().length()
                ? legoSetName1 : legoSetName2).orElse(null);

         return longestString;
    }

    /**
     * Visszatér egy Map-el, kulcsa egy String(Lego szett száma) és értéke is egy String (Lego szett témája)
     * @return Map<String,String> kulcsa a LegoSet Number, értéke LegoSet Theme
     */
    public Map<String,String> createMapByNumberAndTheme(){
        return getAll().stream().collect(Collectors.toMap(legoset-> legoset.getNumber(), legoset-> legoset.getTheme()));
    }

    /**
     * Visszatér egy Map-el, aminek kulcsa a Lego szett témája, értéke pedig az hogy hány ilyen témájú szett van
     * @return Map<String,Long> kulcs a LegoSet Theme és értéke pedig az adott témából hány darab szerepel a listába
     */
    public Map<String, Long> createMapWithStringAndLongByGameTheme(){
        return getAll().stream().collect(Collectors.groupingBy(legoSet -> legoSet.getTheme(),Collectors.counting()));
    }



    public static void main(String[] args) {
        var repository = new LegoSetRepository();
        System.out.println(repository.countLegoSetsWithTag("Microscale"));

        System.out.println("Is there a Lego set where number and year match: "+repository.checkIfWeHaveLegoSetNumberAndYearEqual());
        System.out.println("--------------");
        repository.printLegoSetsFirstTwoLetter();
        System.out.println("--------------");

        System.out.println(repository.printLongestLegoSetNameData());

        System.out.println("----------------------");
        System.out.println(repository.createMapByNumberAndTheme());

        System.out.println("------------------");
        System.out.println(repository.createMapWithStringAndLongByGameTheme());
    }

}
