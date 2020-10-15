package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class ShipmentRelationships extends BaseObject {

    private Assembly assembly;
    private AssemblyCheckList assemblyCheckList;
    private Tickets tickets;
    private LogisticAttributes logisticAttributes;

    public Assembly getAssembly() {
        return assembly;
    }

    public void setAssembly(Assembly assembly) {
        this.assembly = assembly;
    }

    public AssemblyCheckList getAssemblyCheckList() {
        return assemblyCheckList;
    }

    public void setAssemblyCheckList(AssemblyCheckList assemblyCheckList) {
        this.assemblyCheckList = assemblyCheckList;
    }

    public Tickets getTickets() {
        return tickets;
    }

    public void setTickets(Tickets tickets) {
        this.tickets = tickets;
    }

    public LogisticAttributes getLogisticAttributes() {
        return logisticAttributes;
    }

    public void setLogisticAttributes(LogisticAttributes logisticAttributes) {
        this.logisticAttributes = logisticAttributes;
    }

}
