import java.util.ArrayList;
import java.util.List;

public class Node {

	boolean leaf; //True if this node is a leaf, else false.
	Node parent;	// The node parent of this node, null if this node is the root of a tree
	int keys[]; 	// Contains all the value inside this node
	Node sons[];	// Contains all the child of this node
	int countSons;	// the number of child
	int countKeys;	// the number of keys
	int u;  //the maximum number of value that can be inside a node
	int l;  //the minimum number of value that can be inside a node

	public Node( int key , int l , int u) {
		this.parent=null;
		this.keys= new int[u-1];//taille:u-1
		for(int i=0;i<u-1;i++) {
			this.keys[i]=0;
		}
		this.keys[0]=key;
		this.sons=new Node[u]; //taille: u
		for(int i=0;i<u;i++) {
			this.sons[i]=null;
		}
		this.leaf=true;
		this.u=u;
		this.l=l;
		this.countSons=0;
		this.countKeys=1;

	}

	public Node(int key,int l , int u,Node parent) {
		this(key,l,u);
		this.setParent(parent) ;
	}

	public void addKey(int val) {
		for(int i=0;i<this.u-1;i++)
			if(this.keys[i]==0) {
				this.keys[i]=val;
				this.countKeys+=1;
				break;
			}
		else if (this.keys[i]>val){
			this.addAndDecale(i, val) ;
			this.countKeys+=1;
			break;
		}
	}

	public void addAndDecale(int index, int key){
		if (!this.isFullKey()){
			int inter = this.keys[index] ;
			int inter1 = this.keys[index] ;
			for (int i=index+1; i<this.u-1; i++){
				int inter2 = this.keys[i] ;
				this.keys[i] = inter1 ;
				inter1 = inter2 ;
		}
		this.keys[index] = key ;
		this.keys[index+1] = inter ;
	}
	}

	public void addSon(Node son) {
		int i;
		for(i=0;i<this.u;i++){
			if(this.sons[i]==null) {
				this.sons[i]=son;
				this.countSons+=1;
				break;
			}
			else if (this.sons[i].getKeys()[0]>son.getKeys()[0]){
				this.addSonAndDecale(i, son) ;
				this.countSons+=1;
				break;
			}
		}
	}

	public void addSonAndDecale(int index, Node son){
		if (!this.isFullSon()){
			Node inter = this.sons[index] ;
			Node inter1 = this.sons[index] ;
			for (int i=index+1; i<this.u; i++){
				Node inter2 = this.sons[i] ;
				this.sons[i] = inter1 ;
				inter1 = inter2 ;
		}
		this.sons[index] = son ;
		//this.sons[index+1] = inter ;
		}
	}

	public int[] getKeys(){
		return this.keys;
	}

	public Node[] getSons(){
		return this.sons;
	}

	public void leafFalse() {
		this.leaf=false;
	}

	public boolean isLeaf() {
		return this.leaf;
	}

	public int getCountSons() {
		int compteur = 0 ;
		for(int i=0; i<this.u;i++){
			if(this.sons[i] != null){
				compteur+=1 ;
			}
		}
		return compteur;
	}

	public int getCountKeys() {
		int compteur = 0 ;
		for(int i=0; i<this.u-1;i++){
			if(this.keys[i] != 0){
				compteur+=1 ;
			}
		}
		return compteur;
	}

	public Node getParent(){
		return this.parent ;
	}

	public void setParent(Node node){
		this.parent = node ;
		node.addSon(this) ;
	}

	public void becomeRoot(){
		this.parent = null ;
	}

	public boolean isFullKey(){
		return this.countKeys >= this.u-1 ;
	}

	public boolean isFullSon(){
		return this.getCountSons() >= this.u ;
	}

	public Node getSonByIndex(int index) {
		if (index > -1 && index <this.u){
			return this.sons[index];
		}
		else {
			return null ;
		}
	}

	public void deleteKey(int index){
		int i = index ;
		this.keys[i] = 0 ;
		while(i<this.u-2 ){
			this.keys[i]= this.keys[i+1] ;
			this.keys[i+1] = 0 ;
			i++ ;
		}
		this.countKeys-- ;
	}

	public void deleteSon(int index){
		int i = index ;
		this.sons[i] = null ;
		while(i<this.u-1 && this.sons[i+1] != null){
			this.sons[i]= this.sons[i+1] ;
			this.sons[i+1] = null ;
			i++ ;
		}
		this.countSons-- ;
	}

	public Node nodeRight(Node son) {
        int i;
        //sur countSons-1 au moins on fait pas le dernier comme il n a pas de +1 au dernier
        for(i=0;i<this.getCountSons()-1;i++) {
            if(this.sons[i] == son)
                return this.sons[i+1];
        }
        return null;
    }


    public Node nodeLeft(Node son) {
			int i;
			//sur countSons-1 au moins on fait pas le dernier comme il n a pas de +1 au dernier
			for(i=1;i<this.getCountSons();i++) {
					if(this.sons[i] == son)
							return this.sons[i-1];
			}
			return null;
    }

    public int getKeyByIndex(int index) {
			if (index > -1 && index <this.u-1){
				return this.keys[index];
			}
			else {
				return 0 ;
			}
    }

    //attention on remplace la valeur sans rien regarder
    public void replaceSon(int index, Node node) {
        this.sons[index]=node;
    }

    //attention on remplace la valeur sans rien regarder
    public void replaceKey(int index, int key) {
        this.keys[index]=key;
    }

    public int getIndexNode(Node node) {
        int i;
        for(i=0;i<this.getCountSons();i++) {
            if(this.sons[i] == node)
                return i;
        }
        return -1;
    }

}
