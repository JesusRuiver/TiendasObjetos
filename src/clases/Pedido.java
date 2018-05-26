package clases;

public class Pedido {

	private String nif;
	private String nombreArticulo;
	private Fabricante fabricante;
	private Articulo articulo;
	private int codFabricante;
	private int peso;
	private String categoria;
	private String fechaPedido;
	private int unidadesPedidas;

	public Pedido() {

	}

	public Pedido(String nif, String nombreArticulo, Fabricante fabricante, Articulo articulo, int codFabricante,
			int peso, String categoria, String fechaPedido, int unidadesPedidas) {
		
		this.nif = nif;
		this.nombreArticulo = nombreArticulo;
		this.fabricante = fabricante;
		this.articulo = articulo;
		this.codFabricante = codFabricante;
		this.peso = peso;
		this.categoria = categoria;
		this.fechaPedido = fechaPedido;
		this.unidadesPedidas = unidadesPedidas;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public int getCodFabricante() {
		return codFabricante;
	}

	public void setCodFabricante(int codFabricante) {
		this.codFabricante = codFabricante;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public int getUnidadesPedidas() {
		return unidadesPedidas;
	}

	public void setUnidadesPedidas(int unidadesPedidas) {
		this.unidadesPedidas = unidadesPedidas;
	}

	@Override
	public String toString() {
		return "Pedido [nif=" + nif + ", nombreArticulo=" + nombreArticulo + ", fabricante=" + fabricante
				+ ", articulo=" + articulo + ", codFabricante=" + codFabricante + ", peso=" + peso + ", categoria="
				+ categoria + ", fechaPedido=" + fechaPedido + ", unidadesPedidas=" + unidadesPedidas + "]";
	}

	


}
