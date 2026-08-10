package br.gov.es.pmo.sigef_core.model;

import com.fasterxml.jackson.databind.JsonNode;

public interface ISigefProvider {
    
    public JsonNode getBudgetUnitList();
    
    public JsonNode getBudgetPlanList();
    
    public JsonNode getInstrumentsList(String codPO, String codUO, int startYear, int endYear);
    
    public JsonNode getLiquidatedValueByBudgetPlan(String codPO, String codUO);
    
    public JsonNode getResourceSourceList();
    
    
    
}