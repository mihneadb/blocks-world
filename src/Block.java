public class Block {
    private String name;

    public Block(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Block)) {
            return super.equals(obj);
        }
        Block other = (Block) obj;
        return name.equals(other.name);
    }
}
