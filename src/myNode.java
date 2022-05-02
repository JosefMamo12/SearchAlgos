//import java.util.Objects;
//
//public class myNode {
//    private final String color;
//    private final int value;
//    private final String info;
//
//    public myNode(String color) {
//        this.color = color;
//        this.value = putValueBasedOnColor(color);
//        this.info= "";
//    }
//
//    private int putValueBasedOnColor(String color) {
//        switch (color){
//            case "R":
//            case "Y":
//                return 1;
//            case "B":
//                 return 2;
//            case "G":
//                return 10;
//            default:
//                return 0;
//        }
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        myNode myNode = (myNode) o;
//        return Objects.equals(color, myNode.color);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(color);
//    }
//
//    @Override
//    public String toString() {
//        return  color + '\'';
//    }
//}
