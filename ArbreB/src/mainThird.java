
public class mainThird {

	public static void main(String[] args) {

		Node root = new Node(2,2,3);
		Btree tree = new Btree(root,2,3);


		int [] values = {4, 5, 6, 8, 10,14, 16, 18};
		tree.insert( values);
		tree.delete(18, tree.getRoot());
		tree.delete(16, tree.getRoot());
		tree.delete(12, tree.getRoot());
		tree.delete(14, tree.getRoot());
		tree.delete(6, tree.getRoot());

		tree.affichage(tree.getRoot());
		System.out.println("*********************************") ;



	}

}
