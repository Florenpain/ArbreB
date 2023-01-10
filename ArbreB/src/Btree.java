import java.util.List;

public class Btree {

	public int l; //the minimum number of value that can be inside a node
	public int u; //the maximum number of value that can be inside a node
	public int size;  // represents the total number of node inside the tree
	public Node root;	// represents the root node for the tree

	public Btree(Node node,int l , int u) {
		this.l=l;
		this.u=u;
		this.size=1;
		this.root=node;
	};

/*
* @param node the root node we are searching in
* @param x the value we are looking for in the tree
*
* @return true if the value x is in the tree, else false
*/

	public Node getRoot(){
		return this.root ;
	}

	public boolean isRoot(Node node){
		return this.root == node ;
	}

	public void setRoot(Node node){
		this.root = node ;
	}

	public boolean search(Node node , int x) {

		//if node is leaf , we return if value is part of the keys
		if(node.isLeaf()) {
			return (this.contains(node,x));
		}
		else{
			if(this.contains(node,x)) {
				return true ;
			}
			else{
				int i=0; //it represents the index of the son's node in the list
				while(i < this.u-1 && x>node.getKeys()[i] &&  node.getKeys()[i] != 0)
					i+=1; // we are looking for the index of the son's node we are going to search the value x
				return search(node.getSons()[i],x); //Recursive call on son's node

			}
		}
	}

	public boolean contains(Node node, int key) {
		int f = 0;
		int l = node.getCountKeys() ;
		int mid = (f + l)/2;
		while(f <= l){
			if (node.getKeys()[mid] < key){
				f = mid + 1;
			}else if(node.getKeys()[mid] == key){
				System.out.println("L'élément se trouve à l'index: " + mid);
				return true;
			}else{
				 l = mid - 1;
			}
			mid = (f + l)/2;
	 }
		if (f > l){
			// System.out.println("L'élément n'existe pas!");
			return false ;
		}
		return false ;
	}

	public void affichage(Node node) {
		if(node != null){
			this.printKeys(node);
			System.out.println("") ;
			for(int i=0;i<this.u;i++) {
				this.affichage(node.getSons()[i]) ;
			}
		}
	}

	public void printKeys(Node node) {
		System.out.print("Ce noeud contient les valeurs suivantes:");
		for(int i = 0 ; i<this.u-1 ; i++ ) {
			if (node.getKeys()[i] != 0){
			System.out.print(node.getKeys()[i] + "(" + i + ") ");
			}
		}
		System.out.println("");
		if(node.getParent() != null){
			System.out.print("son parent contient les valeurs suivantes:");
			for(int i = 0 ; i<this.u-1 ; i++ ) {
				if (node.getParent().getKeys()[i] != 0){
				System.out.print(node.getParent().getKeys()[i] + "(" + i + ") ");
			}
			}
			System.out.println("");
		}
	}


	//si c ets pas une feuille on va dans les enfants sinon on rajoute dans le node
	//l enfant correspondant (niveau valeur , key > val < key+1
	// si c est une feuille on rajoute , si elle full :
	//	on divise en deux et on remonte la valeur medianne
	//on ajoute la valeur medianne au parent
	//on regarde si c est un arbre  �quilibr�

	public boolean insert(int key, Node node) {
		if (node.isLeaf()){
			if(node.isFullKey()){
			return this.split(node, key) ;
		}
		else {
			node.addKey(key) ;
			return true ;
		}
	}
	else {
		int i=0; //it represents the index of the son's node in the list
		while(i < this.u-1 && key>node.getKeys()[i] &&  node.getKeys()[i] != 0)
			i+=1; // we are looking for the index of the son's node we are going to search the value x
		return insert(key, node.getSons()[i]);
	}
		}

	public boolean insert(int[] keys){
		for(int i=0; i<keys.length ; i++){
			this.insert(keys[i], this.root) ;
		}
		return true ;
	}

	public boolean split(Node node, int key){
		int mid = (node.getCountKeys())/2 ;
		if(node.isLeaf()){
			if(node.getParent() != null){
				if(!node.getParent().isFullKey()){
					node.getParent().addKey(node.getKeys()[mid]) ;
					return this.splitLeaf(node, key);
				}
				else{
					split(node.getParent(), node.getKeys()[mid]) ;
					return this.splitLeaf(node, key);
				}
			}
			else {
				Node parent = new Node(node.getKeys()[mid], this.l, this.u) ;
				parent.leafFalse() ;
				this.root = parent ;
				node.setParent(parent) ;
				return this.splitLeaf(node, key) ;
			}
		}
		else {
			if(node.getParent() != null){
				if(!node.getParent().isFullKey()){
					node.getParent().addKey(node.getKeys()[mid]) ;
					return this.splitNode(node, key);
				}
				else{
					split(node.getParent(), node.getKeys()[mid]) ;
					return this.splitNode(node, key);
				}
			}
			else{
				Node parent = new Node(node.getKeys()[mid], this.l, this.u) ;
				parent.leafFalse();
				this.root = parent ;
				node.setParent(parent) ;
				return this.splitNode(node, key);
			}
			}
		}

	public boolean splitLeaf(Node node, int key){
		int mid = (node.getCountKeys())/2 ;
		node.deleteKey(mid) ;
		if(mid+1 < node.getCountKeys()){
			Node nodeRight = new Node(node.getKeys()[mid],this.l,this.u,node.getParent());
			node.deleteKey(mid) ;
			for (int i = mid+2 ; i<=this.u-2; i++){
				nodeRight.addKey(node.getKeys()[mid]);
				node.deleteKey(mid);
			}
			nodeRight.addKey(key) ;
		}
		else{
			Node nodeRight = new Node(key,this.l,this.u,node.getParent());
		}

		return true ;
	}

	public boolean splitNode(Node node, int key){
		int mid = (node.getCountKeys())/2 ;
		node.deleteKey(mid) ;
		if(mid+1 < node.getCountKeys()){
			Node nodeRight = new Node(node.getKeys()[mid],this.l,this.u,node.getParent());
			nodeRight.leafFalse() ;
			node.deleteKey(mid) ;
			node.getSonByIndex(mid+1).setParent(nodeRight) ;
			node.deleteSon(mid+1) ;
			for (int i = mid+2 ; i<=this.u-2; i++){
				nodeRight.addKey(node.getKeys()[mid]);
				node.getSonByIndex(mid+1).setParent(nodeRight) ;
				node.deleteKey(mid);
				node.deleteSon(mid+1);
			}
			if (node.getSonByIndex(mid+1)!= null){
				node.getSonByIndex(mid+1).setParent(nodeRight) ;
				node.deleteSon(mid+1);
			}
			if (key > node.getKeys()[mid-1]){
				nodeRight.addKey(key) ;
			}
			else {
				node.addKey(key) ;
			}
		}
			else{
				Node nodeRight = new Node(key,this.l,this.u,node.getParent());
				nodeRight.leafFalse() ;
				node.getSonByIndex(mid+1).setParent(nodeRight) ;
				node.deleteSon(mid+1);
			}
			node.deleteSon(mid+1) ;
		return true ;
	}
	// true si toute les feuilles ont la m�me hauteur

	public boolean balanced(Node node) {
		return (0 != hauteur(node));
	}


	public int hauteur(Node node){
		if(node.isLeaf())
			return 1;
		int i;
		int tab[]=new int[node.getCountSons()];
		for (i=0;i<node.getCountSons();i++) {
			tab[i]=hauteur(node.getSonByIndex(i));
		}

		for (i=0;i<node.getCountSons();i++) {
			if(tab[i] != tab[0])
				return 0;
		}
		return tab[0];
	}

	public int containsIndex(Node node, int key) {
        int f = 0;
        int l = node.getCountKeys() ;
        int mid = (f + l)/2;
        while(f <= l){
            if (node.getKeyByIndex(mid) < key){
                f = mid + 1;
            }else if(node.getKeys()[mid] == key){
                //System.out.println("L'element se trouve a l'index: " + mid);
                return mid;
            }else{
                 l = mid - 1;
            }
            mid = (f + l)/2;
     }
        if (f > l){
            // System.out.println("L'Ã©lÃ©ment n'existe pas!");
            return  -1 ;
        }
        return -1 ;
    }

		public boolean delete(int[] keys){
				for(int i=0; i<keys.length ; i++){
					this.delete(keys[i], this.root) ;
				}
				return true ;
		}

		public boolean delete(int key, Node node) {
			int indexKey;
			if((indexKey=this.containsIndex(node,key))!=-1){
				Node nodeVoisin;
				if(node.isLeaf()){
					if(node.getCountKeys()>this.l-1) {
						node.deleteKey(indexKey);
						return true;
					}
					else if((nodeVoisin=node.getParent().nodeRight(node)) != null && nodeVoisin.getCountKeys()>this.l-1 ) {
						System.out.println("Rotation Gauche") ;
						this.rotationGauche(node, indexKey) ;
						return true;
					}
					else if((nodeVoisin=node.getParent().nodeLeft(node)) != null && nodeVoisin.getCountKeys()>this.l-1) {
						System.out.println("Rotation Droite") ;
						this.rotationDroite(node, indexKey) ;
						return true;
					}
					else{
						if((nodeVoisin=node.getParent().nodeRight(node)) != null) {
							node.deleteKey(indexKey);
							System.out.println("Fusion Droite") ;
							this.fusion(node, nodeVoisin);
						}
						else if ((nodeVoisin=node.getParent().nodeLeft(node)) != null ){
							node.deleteKey(indexKey);
							System.out.println("Fusion Gauche") ;
							this.fusion(nodeVoisin, node);
						}
						else {
							System.out.println("Delete pb pas de voisin de gauche  et n y de voisin de droite (dans delete)");
							return false;
						}
					}
				}
				else{
					Node leftChild = node.getSonByIndex(indexKey);
					Node rightChild = node.getSonByIndex(indexKey+1);
					if(rightChild != null){
						int val = this.getValueMin(rightChild) ;
						node.replaceKey(indexKey, val) ;
						this.delete(val, rightChild) ;
					}
					else if(leftChild != null){
						int val = this.getValueMax(leftChild) ;
						node.replaceKey(indexKey, val) ;
						this.delete(val, leftChild) ;
					}
					else {
						System.out.println("aucun enfant à droite ou à gauche") ;
					}
				}
			}
			else {
				if(node.isLeaf()){
					return false ;
				}
				//si le neoud ne contient pas la clé et on est pas dnas une feuille on recherche le neoud pour refaire une recherche
				int i=0; //it represents the index of the son's node in the list
				while(i < this.u-1 && key>node.getKeys()[i] &&  node.getKeys()[i] != 0)
					i+=1; // we are looking for the index of the son's node we are going to search the value x
				return delete(key, node.getSons()[i]);
			}
			return false ;
		}


		// rotation vers la gauche
		private void rotationGauche(Node node, int indexKey){
			int indexNode = node.getParent().getIndexNode(node) ;
			Node voisinDroite = node.getParent().getSonByIndex(indexNode+1) ;
			if(voisinDroite != null){
				node.deleteKey(indexKey);
				node.addKey(node.getParent().getKeyByIndex(indexNode));
				node.getParent().replaceKey(indexNode, voisinDroite.getKeyByIndex(0));
				voisinDroite.deleteKey(0);
			}
			else{
				System.out.println("rotation impossible : aucun frère à droite") ;
			}
		}

		private void rotationDroite(Node node, int indexKey){
			int indexNode = node.getParent().getIndexNode(node) ;
			Node voisinGauche = node.getParent().getSonByIndex(indexNode-1) ;
			if(voisinGauche != null){
				node.deleteKey(indexKey);
				node.addKey(node.getParent().getKeyByIndex(indexNode-1));
				node.getParent().replaceKey(indexNode-1, voisinGauche.getKeyByIndex(voisinGauche.getCountKeys()-1));
				voisinGauche.deleteKey(voisinGauche.getCountKeys()-1);
			}
			else{
				System.out.println("rotation impossible : aucun frère à gauche") ;
			}
		}

		private void fusion(Node node1, Node node2){
				int index = node1.getParent().getIndexNode(node1) ;
				node1.addKey(node1.getParent().getKeyByIndex(index)) ;
				for(int i=0 ; i<this.u-1 ; i++){
					int key = node2.getKeyByIndex(i) ;
					Node node = node2.getSonByIndex(i) ;
					if (key!= 0){
						node1.addKey(key) ;
					}
					if (node != null){
						node.setParent(node1) ;
					}
				}
				Node node = node2.getSonByIndex(this.u) ;
				if (node != null){
					node.setParent(node1) ;
				}
				node1.getParent().deleteSon(node2.getParent().getIndexNode(node2)) ;
				node1.getParent().deleteKey(index) ;
				Node voisinParentDroit ;
				Node voisinParentGauche ;
				if (node1.getParent().getCountKeys() < this.l-1 && this.isRoot(node1.getParent())){
					Node nodeVoisin ;
					if((nodeVoisin=node1.getParent().nodeRight(node)) != null){
						this.fusion(node1, nodeVoisin) ;
					}
					else if((nodeVoisin=node1.getParent().nodeLeft(node)) != null){
						this.fusion(nodeVoisin, node1) ;
					}
					else{
						this.setRoot(node1) ;
						node1.becomeRoot() ;
					}
				}
				else if(node1.getParent().getCountKeys() < this.l-1 && (voisinParentDroit =node1.getParent().getParent().nodeRight(node1.getParent())) != null){
					this.fusion(node1.getParent(), voisinParentDroit) ;
				}
				else if(node1.getParent().getCountKeys() < this.l-1 && (voisinParentGauche = node1.getParent().getParent().nodeLeft(node1.getParent()) )!= null){
					this.fusion(voisinParentGauche, node1.getParent()) ;
				}
		}

		public int getValueMax(Node node){
			if(node != null){
				if(node.isLeaf()){
					return node.getKeyByIndex(node.getCountKeys()-1) ;
				}
				else{
					return this.getValueMax(node.getSonByIndex(node.getCountSons()-1)) ;
				}
			}
			else{
				System.out.println("probleme getValueMax") ;
				return 0 ;
			}
		}

		public int getValueMin(Node node){
			if(node != null){
				if(node.isLeaf()){
					return node.getKeyByIndex(0) ;
				}
				else{
					return this.getValueMin(node.getSonByIndex(0)) ;
				}
			}
			else{
				System.out.println("probleme getValueMax") ;
				return 0 ;
			}
		}
}
