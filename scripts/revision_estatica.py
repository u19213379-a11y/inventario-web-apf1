"""Validación estructural sin dependencias externas; no sustituye Maven/Tomcat."""
from pathlib import Path
import xml.etree.ElementTree as ET

root = Path(__file__).resolve().parents[1]
mandatory = [
    'pom.xml', 'database/inventario_apf2.sql',
    'src/main/webapp/WEB-INF/web.xml', 'src/main/webapp/WEB-INF/faces-config.xml',
    'src/main/webapp/login.xhtml', 'src/main/webapp/dashboard.xhtml',
    'src/main/webapp/productos.xhtml', 'src/main/webapp/movimientos.xhtml',
    'src/main/webapp/reporte-stock.xhtml', 'src/main/webapp/categorias.xhtml',
    'src/main/java/pe/utp/inventario/bean/SesionBean.java',
    'src/main/java/pe/utp/inventario/bean/ProductoBean.java',
    'src/main/java/pe/utp/inventario/dao/ProductoDAO.java',
    'src/main/java/pe/utp/inventario/dao/MovimientoDAO.java',
]
for path in mandatory:
    assert (root/path).exists(), f'Falta: {path}'
for path in list(root.rglob('*.xhtml')) + list(root.rglob('*.xml')):
    ET.parse(path)
assert '*.xhtml' in (root/'src/main/webapp/WEB-INF/web.xml').read_text()
assert '<h:dataTable' in (root/'src/main/webapp/productos.xhtml').read_text()
assert 'FOR UPDATE' in (root/'src/main/java/pe/utp/inventario/dao/MovimientoDAO.java').read_text()
print(f'OK: {len(mandatory)} archivos obligatorios y XML/XHTML bien formados.')
print('ATENCIÓN: comprobar separadamente con mvn clean package, Tomcat 9 y MySQL 8.')
