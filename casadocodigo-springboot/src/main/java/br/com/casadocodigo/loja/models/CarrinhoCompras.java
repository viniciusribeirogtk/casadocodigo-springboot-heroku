package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CarrinhoCompras  implements Serializable{

	private static final long serialVersionUID = 1L;

	private transient Map<CarrinhoItem, Integer> itens;
	
	private int quantidade;
	
	public CarrinhoCompras() {
		this.itens = new LinkedHashMap<CarrinhoItem, Integer>();
	}
	
	public void add(CarrinhoItem item){
		itens.put(item, getQuantidade(item) + 1);
		this.quantidade ++;
	}

	public int getQuantidade(CarrinhoItem item) {
		if (!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		return itens.get(item);
	}
	
	public int getQuantidade(){
		return this.quantidade;
//	   return itens.values().stream().reduce(0, (proximo, acumulador) -> (proximo + acumulador));
	}
	
	public Collection<CarrinhoItem> getItens() {
	    return itens.keySet();
	}
	
	public BigDecimal getTotal(CarrinhoItem item){
	    return item.getTotal(getQuantidade(item));
	}
	
	public BigDecimal getTotal(){
	    BigDecimal total = BigDecimal.ZERO;
	    for (CarrinhoItem item : itens.keySet()) {
	        total = total.add(getTotal(item));
	    }
	    return total;
	}

	public void remove(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto();
		produto.setId(produtoId);
		itens.remove(new CarrinhoItem(produto, tipoPreco));
	}
}
