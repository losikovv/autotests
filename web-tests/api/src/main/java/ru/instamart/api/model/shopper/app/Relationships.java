package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class Relationships extends BaseObject {
	private AssemblyCheckListSHP assemblyCheckList;
	private TicketsSHP tickets;
	private AssemblySHP assembly;
	private LogisticAttributesSHP logisticAttributes;
}