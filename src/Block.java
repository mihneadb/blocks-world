public class Block {
    // The name that identifies the block
    private String name;

    /* 
        The constructor to make a Block object with the given name,
        must be created with name.
    */
    public Block(String name) {
        this.name = name;
    }

    /*
        Returns the name of the block. The global variable is 
        private within the class, so this is used as a getter
        for the name.
    */
    public String getName() {
        return name;
    }

    /*
        This function returns the name of the block, it functions
        as a getter for the name of the block.
    */
    @Override
    public String toString() {
        return name;
    }

    /*
       This function evaluates the equality between 'this' block
       and the given object in the parameters. This uses a super 
       equals to override the access permissions of the child
       and use the parent to check equality if the object is 
       not a block. Otherwise it simply returns the boolean of
       it's regular equality.
    */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Block)) {
            return super.equals(obj);
        }
        Block other = (Block) obj;
        return name.equals(other.name);
    }
}
