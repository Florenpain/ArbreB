public class mainSecond {

	public static void main(String[] args) {

		Node root = new Node(10,6,11);
		Btree tree = new Btree(root,6,11);

		for (int i = 2 ; i < 500 ; i++){
			//System.out.println("insertion de" + i*10 + " :") ;
			tree.insert( i*10, tree.getRoot());
		}
		for (int i = 5 ; i < 4995 ; i+=10){
			//System.out.println("insertion de" + i + " :") ;
			tree.insert( i, tree.getRoot());
		}
		tree.affichage(tree.getRoot());
	}
}
