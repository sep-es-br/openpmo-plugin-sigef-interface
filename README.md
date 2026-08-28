# openpmo-plugin-sigef-interface

Contrato para integrar o OpenPMO a implementações responsáveis por consultar dados orçamentários e financeiros no SIGEF.

## Objetivo

Este projeto desacopla o OpenPMO dos detalhes técnicos da integração com o SIGEF. Ele define somente a interface que um plugin deve implementar para fornecer unidades e planos orçamentários, instrumentos, valores liquidados, fontes de recursos e centros de custo.

Com esse contrato, o OpenPMO pode consumir essas informações sem conhecer autenticação, URLs, protocolos de comunicação ou formatos específicos do serviço de origem.

## Responsabilidades

O projeto contém:

- `ISigefProvider`, contrato das consultas disponibilizadas ao OpenPMO;
- a dependência pública do Jackson necessária para representar as respostas como `JsonNode`.

O projeto não contém:

- chamadas HTTP ou clientes do SIGEF;
- autenticação e gerenciamento de credenciais;
- persistência;
- tratamento ou transformação de respostas específicas de uma integração;
- implementação concreta de `ISigefProvider`.

## Requisitos

- Java 11 ou superior;
- repositório JitPack configurado no projeto consumidor.

## Instalação

Adicione o JitPack aos repositórios do Gradle:

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

Adicione o contrato como dependência:

```groovy
dependencies {
    implementation 'com.github.sep-es-br:openpmo-plugin-sigef-interface:1.1.0'
}
```

## Contrato do provider

```java
public interface ISigefProvider {

    JsonNode getBudgetUnitList() throws Exception;

    JsonNode getBudgetPlanList(String codBU) throws Exception;

    JsonNode getInstrumentsList(
        String codBU,
        long startYear,
        long endYear
    ) throws Exception;

    JsonNode getLiquidatedValueByBudgetPlan(
        String codBP,
        String codBU
    ) throws Exception;

    @Deprecated(forRemoval = false)
    default JsonNode getResourceSourceList() {
        throw new UnsupportedOperationException(
            "O método getResourceSourceList ainda não é suportado na versão atual."
        );
    }

    JsonNode getCostCenterList();
}
```

| Método | Finalidade |
| --- | --- |
| `getBudgetUnitList()` | Consulta a lista de unidades orçamentárias. |
| `getBudgetPlanList(String codBU)` | Consulta os planos orçamentários de uma unidade orçamentária. |
| `getInstrumentsList(String codBU, long startYear, long endYear)` | Consulta contratos e acordos de uma unidade orçamentária, filtrados pelo período informado. |
| `getLiquidatedValueByBudgetPlan(String codBP, String codBU)` | Consulta o valor liquidado de um plano orçamentário em uma unidade orçamentária. |
| `getResourceSourceList()` | Ponto de extensão futuro para consultar fontes de recursos. A implementação padrão não oferece suporte à operação. |
| `getCostCenterList()` | Consulta a lista de centros de custo. |

### Parâmetros

| Parâmetro | Tipo | Descrição |
| --- | --- | --- |
| `codBU` | `String` | Código da unidade orçamentária. |
| `codBP` | `String` | Código do plano orçamentário. |
| `startYear` | `long` | Ano inicial do período de consulta dos instrumentos. |
| `endYear` | `long` | Ano final do período de consulta dos instrumentos. |

## Dados retornados

Todos os métodos retornam `JsonNode`. O contrato não determina a estrutura interna do JSON: cabe à implementação concreta consultar o sistema de origem e devolver os dados no formato esperado pelo OpenPMO.

Os métodos que declaram `throws Exception` permitem que a implementação sinalize falhas ocorridas durante a consulta. O plugin concreto deve adotar o tratamento de erros apropriado à integração.

`getResourceSourceList()` é um método de extensão para integração futura. Como sua implementação padrão lança `UnsupportedOperationException`, o consumidor não deve presumir que essa consulta está disponível sem verificar o suporte oferecido pelo plugin concreto.

## Criando uma implementação

Um plugin de integração com o SIGEF deve depender deste projeto e implementar `ISigefProvider`:

```java
public class SigefProvider implements ISigefProvider {

    @Override
    public JsonNode getBudgetUnitList() throws Exception {
        // Autenticar, consultar o SIGEF e devolver a resposta esperada.
        throw new UnsupportedOperationException("Implementar consulta");
    }

    @Override
    public JsonNode getBudgetPlanList(final String codBU) throws Exception {
        throw new UnsupportedOperationException("Implementar consulta");
    }

    @Override
    public JsonNode getInstrumentsList(
        final String codBU,
        final long startYear,
        final long endYear
    ) throws Exception {
        throw new UnsupportedOperationException("Implementar consulta");
    }

    @Override
    public JsonNode getLiquidatedValueByBudgetPlan(
        final String codBP,
        final String codBU
    ) throws Exception {
        throw new UnsupportedOperationException("Implementar consulta");
    }

    @Override
    public JsonNode getCostCenterList() {
        throw new UnsupportedOperationException("Implementar consulta");
    }
}
```

Caso o projeto consumidor utilize um contêiner de injeção de dependências, a implementação deve ser registrada de acordo com as convenções desse contêiner. Este contrato não depende de nenhum framework de injeção.

## Build local

No Windows:

```powershell
.\gradlew.bat clean build
```

Em Linux ou macOS:

```bash
./gradlew clean build
```
