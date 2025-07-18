package com.jorgerobertoargolo.personal_finance.infrastructure.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Classe utilitária responsável por mapear objetos de um tipo para outro utilizando a biblioteca ModelMapper.
 * <p>
 * Essa classe é útil para conversões entre entidades e DTOs, evitando a necessidade de mapeamentos manuais.
 * A configuração interna do {@link ModelMapper} garante que o mapeamento seja feito com estratégia estrita
 * e acesso aos campos privados, o que promove precisão e consistência.
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 * UsuarioDTO dto = new UsuarioDTO("nome", "email");
 * Usuario usuario = objectMapperUtil.map(dto, Usuario.class);
 * }</pre>
 *
 * @author Jorge Roberto
 */
@Component
public class ObjectMapperUtil {
    private static final ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new ModelMapper();
    }

    /**
     * Realiza o mapeamento de um objeto de entrada para uma instância da classe de destino informada.
     *
     * @param object objeto de entrada a ser convertido
     * @param clazz  classe do tipo de saída desejado
     * @param <Input>  tipo do objeto de entrada
     * @param <Output> tipo do objeto de saída
     * @return nova instância da classe de saída com os dados mapeados do objeto de entrada
     */
    public <Input, Output> Output map(final Input object, final Class<Output> clazz) {

        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        Output c = MODEL_MAPPER.map(object, clazz);

        return c;
    }

    /**
     * Converte uma lista de objetos para outra lista de objetos de classe diferente.
     *
     * @param sourceList lista de objetos de entrada
     * @param clazz      classe de destino
     * @param <Input>    tipo da entrada
     * @param <Output>   tipo da saída
     * @return lista de objetos convertidos
     */
    public <Input, Output> List<Output> mapAll(final List<Input> sourceList, final Class<Output> clazz) {
        return sourceList.stream()
                .map(source -> map(source, clazz))
                .collect(Collectors.toList());
    }

}
