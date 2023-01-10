
public class mainFirst {

	public static void main(String[] args) {

		Node root = new Node(2,2,3);
		Btree tree = new Btree(root,2,3);


		int [] values = {4, 5, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 7, 9, 11, 13};
		tree.insert( values);
		tree.affichage(tree.getRoot());

		System.out.println("*********************************") ;
		int [] toDelete = {14, 10, 20, 18, 16, 24, 6} ;
		tree.delete(toDelete) ;
		tree.affichage(tree.getRoot()) ;
		System.out.println("*********************************") ;

	}
}
