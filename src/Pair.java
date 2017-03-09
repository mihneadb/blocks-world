public class Pair<T1, T2> {
    private T1 fst;
    private T2 snd;

    /*
      Constructor for the Pair class takes the parameters
      of fst and snd. This pairing class allows for better
      organization of the data.  
    */
    public Pair(T1 fst, T2 snd) {
        this.fst = fst;
        this.snd = snd;
    }

    /*
      Getter for the fst of the pair, fst is a private variable
      so a public getter is used.  
    */
    public T1 getFst() {
        return fst;
    }

    /*
      Getter for the snd of the pair, snd is a private variable
      so a public getter is used.  
    */
    public T2 getSnd() {
        return snd;
    }

}
