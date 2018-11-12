class Contact {
    private String name;
    private String phoneNumber;
    private int outgoingCalls = 0;

    Contact(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    Contact(String name, String phoneNumber, int outboundCalls) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.outgoingCalls = outboundCalls;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    int getOutgoingCalls() {
        return outgoingCalls;
    }

    void addOutgoingCall() {
        outgoingCalls++;
    }

    String printContact() {
        return "Name: " + name + "\nNumber: " + phoneNumber + "\nOutgoing calls: " + outgoingCalls + "\n";
    }
}
