<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8>
<title>Livros de java, Android, Iphone, PHP, Ruby e muito mais -
    Casa do código</title>
<jsp:include page="../components/libs.jsp"/>
</head>
<body>
	<nav class="navbar navbar-inverse">
	  <div class="container">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="${s:mvcUrl('HC#index').build()}">Casa do Código</a>
	    </div>
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	        <li><a href="${s:mvcUrl('PC#listar').build()}">Lista de Produtos</a></li>
	        <li><a href="${s:mvcUrl('PC#form').build()}">Cadastro de Produtos</a></li>
	    </ul>
	    <ul class="nav navbar-nav navbar-right">
	  		<li><a href="#"><security:authentication property="principal.username"/></a></li>
		</ul>
	    </div><!-- /.navbar-collapse -->
	  </div>
	</nav>
	<div class="container">
		<div class="row">
        	<div class="col-md-12">
        		<div class="table-responsive">
					<h1>Lista de Produtos</h1>
					<p>${sucesso}</p>
					<p>${falha}</p>
				    <table class="table table-bordred table-striped">
					    <thead>
					        <tr>
					            <th>Título</th>
					            <th>Descrição</th>
					            <th>Preços</th>
					            <th>Páginas</th>
					            <th>Editar</th>
				                <th>Remover</th>
					        </tr>
						</thead>
						<tbody>
					        <c:forEach items="${produtos}" var="produto">
					            <tr>
					                <td><a href="${s:mvcUrl('PC#detalhe').arg(0, produto.id).build()}">${produto.titulo}</a></td>
					                <td>${produto.descricao}</td>
					                <td>${produto.precos }</td>
					                <td>${produto.paginas}</td>
				                    <td>
				                  		<form:form method="post" servletRelativeAction="/produtos/editar">
					              			<input type="hidden" name="id" value="${produto.id}">
					                    	<p data-placement="top" data-toggle="tooltip" title="Edit">
					                    	<button class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" data-target="#edit" >
					                    		<span class="glyphicon glyphicon-pencil"></span>
					                    	</button>
				                    	</p>
					                   	</form:form>
				                    </td>
				 					<td>
									 	<form:form method="post" servletRelativeAction="/produtos/remover">
					              			<input type="hidden" name="id" value="${produto.id}">
										 	<p data-placement="top" data-toggle="tooltip" title="Delete">
						 						 	<button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" >
						 						 		<span class="glyphicon glyphicon-trash"></span>
					 						 		</button>
										 	</p>
				 						 </form:form>
									</td>
					            </tr>
					        </c:forEach>
				        </tbody>
				    </table>
			    </div>
		    </div>
	    </div>
    </div>
</body>
</html>