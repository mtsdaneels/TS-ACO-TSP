package TS_ACO_TSP;

/**
 * Class representing a four tuple.
 */
public class FourTuple {
        /**
         * Node representing the node before node i.
         */
        private DLL.Node nodeBefI;

        /**
         * Returns the node before node i.
         */
        public DLL.Node getNodeBefI(){
            return nodeBefI ;
        }

        /**
         * Node representing node i.
         */
        private DLL.Node nodeI;

        /**
         * Returns node i.
         */
        public DLL.Node getNodeI(){
            return nodeI ;
        }

        /**
         * Node representing node j.
         */
        private DLL.Node nodeJ;

        /**
         * Returns node j.
         */
        public DLL.Node getNodeJ(){
            return nodeJ ;
        }

        /**
         * Node representing the node after node j.
         */
        private DLL.Node nodeAfterJ;

        /**
         * Returns the node after node j.
         */
        public DLL.Node getNodeAfterJ(){
            return nodeAfterJ ;
        }

        /**
         * Constructor initializing a new 4-tuple.
         * @param nodeBefI Node representing the node before node i.
         * @param nodeI Node representing node i.
         * @param nodeJ Node representing node j.
         * @param nodeAfterJ Node representing the node after node j.
         */
        public FourTuple(DLL.Node nodeBefI, DLL.Node nodeI, DLL.Node nodeJ, DLL.Node nodeAfterJ){
            this.nodeBefI = nodeBefI;
            this.nodeI = nodeI;
            this.nodeJ = nodeJ;
            this.nodeAfterJ = nodeAfterJ;
        }

}
