package br.gov.es.openpmo.sigef_core.model;

import com.fasterxml.jackson.databind.JsonNode;

public interface ISigefProvider {
    
    /**
     * Busca a lista de Unidades Orçamentárias
     * 
     * @return JsonNode com as unidades
     * @throws Exception
     */
    public JsonNode getBudgetUnitList() throws Exception;
    
    /**
     * Busca a lista de Planos Orçamentarios 
     * 
     * @param codBU código da Unidade Orçamentária
     * @return
     * @throws Exception 
     */
    public JsonNode getBudgetPlanList(String codBU) throws Exception;
    
    /**
     * Busca a lista de Instrumentos(Contratos e Acordos) de uma Unidade Orçamentária
     * 
     * @param codBU código da Unidade Orçamentária
     * @param startYear filtro do ano de inicio dos contratos de trabalho
     * @param endYear filtro do ano fim dos contratos de trabalho
     * @return JsonNode com a lista das Unidade Orçamentárias
     * @throws Exception 
     */
    public JsonNode getInstrumentsList(String codBU, long startYear, long endYear) throws Exception;
    
    /**
     * Busca o valor liquidado por Plano Orçamentário dentro de um JsonNode
     * 
     * @param codBP 
     * @param codBU
     * @return JsonNode contendo o valor liquidado
     * @throws Exception
     */
    public JsonNode getLiquidatedValueByBudgetPlan(String codBP, String codBU) throws Exception;
    
    /**
     * 
     * Busca a lista de Fontes de Recursos em um JsonNode
     * 
     * @return JsonNode contendo as fontes de recursos
     */
    public JsonNode getResourceSourceList();
    
    /**
     * Retorna a lista de Centros de Custo em um JsonNode
     * 
     * @return JsonNode contendo os centros de custo
     * @deprecated Este método é destinado a integrações futuras e ainda não é utilizado pelo OpenPMO
     */
    @Deprecated(forRemoval = false)
    public default JsonNode getCostCenterList() {
        throw new UnsupportedOperationException("O método getCostCenterList ainda não é suportado na versão atual.");
    }
    
    
}
