package ru.instamart.api.model.shopper.app.active_shipments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Relationships{

	@JsonProperty("routes")
	private Routes routes;

	@JsonProperty("assemblyCheckList")
	private AssemblyCheckList assemblyCheckList;

	@JsonProperty("promotions")
	private Promotions promotions;

	@JsonProperty("tickets")
	private Tickets tickets;

	@JsonProperty("assembly")
	private Assembly assembly;

	@JsonProperty("logisticAttributes")
	private LogisticAttributes logisticAttributes;
}