import java.util.Set;

public class Index {
        int curVertex;
        Set<Integer> vertexSet;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Index index = (Index) o;

            if (curVertex != index.curVertex) return false;
            return !(vertexSet != null ? !vertexSet.equals(index.vertexSet) : index.vertexSet != null);
        }

        @Override
        public int hashCode() {
            int result = curVertex;
            result = 31 * result + (vertexSet != null ? vertexSet.hashCode() : 0);
            return result;
        }

        public static Index createNewIndex(int vertex, Set<Integer> vertexSet) {
            Index i = new Index();
            i.curVertex = vertex;
            i.vertexSet = vertexSet;
            return i;
        }
    }