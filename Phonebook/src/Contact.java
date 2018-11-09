class Contact {
    private String name;
    private String number;
    private int outgoingCalls = 0;

    Contact(String name, String number){
        this.name = name;
        this.number = number;
    }
    Contact(String name, String number, int outboundCalls) {
        this.name = name;
        this.number = number;
        this.outgoingCalls = outboundCalls;
    }

    String getName() {
        return name;
    }

    String getNumber() {
        return number;
    }

    int getOutgoingCalls() {
        return outgoingCalls;
    }

    void addOutgoingCall() {
        outgoingCalls++;
    }

    String printContact() {
        return "Name: " + getName()+ "\nNumber: " + getNumber() +"\nOutgoing calls: " + getOutgoingCalls() + "\n";
    }

}
