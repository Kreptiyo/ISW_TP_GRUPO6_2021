ISW TP1 - GRUPO 6

Miembros del equipo:
<ul>
<li>Mateo Mahieu</li>
<li>Jeremias Molina</li>
<li>Maximiliano Pinto</li>
  <li>Ezequiel Vianco</li>
  </ul>

EJERCICIO 2:

f. PR branch branch-jere -> branch branch-release2: https://github.com/Kreptiyo/ISW_TP_GRUPO6_2021/pull/1

g. Para llevar esto a cabo nos posicionamos en la rama main y realizamos un merge con la rama branch-release1 para luego insertar los cambios mediante un push

j. Nosotros realizamos el commit y push del Cambio B y lo eliminamos con los comando git reset --hard y git push --force

k. Para llevar esto a cabo nos posicionamos en la rama main y realizamos un merge con la rama branch-feature para luego insertar los cambios mediante un push

EJERCICIO 3:

a. En el README pondríamos información esencial para el proyecto, como ser:

<ul>
<li>Comandos o acciones necesarias para instalar el proyecto y/o sus dependencias</li>
<li>Información para su integración y/o modificación en entornos determinados (si la hay) ver</li>
<li>Creadores/dueños del proyecto/licencias</li>
<li>En nuestro caso incluimos las respuestas a las consignas del TP en el README ya que es el propósito principal del proyecto</li>
</ul>

b. A la persona que realize el PR le pediriamos los motivos por el cual quiere hacer los cambios y que en un comentario aparte nos detalle los cambios realizados.

GitHub para ayudarnos con esto nos ofrece un formulario para especificar los detalles de los cambios a realizar y tambien dispone de labels que son etiquetas que se pueden agregar con el fin de tener una mejor organizacion (Bug, question, enhancement, wontfix, etc)

24/09/2021: En base a lo explicado en la clase se procedio a añadir una release sobre la rama main.

---------------------------------------------------------------------------------------------------------

02/11/2021
<ul>
Comandos que utilizamos en orden para la correcion del TP 1:
<li>git branch develop</li>
<li>git checkout develop</li>
<li>git add .</li>
<li>git push --set-upstream origin develop</li>
<li>git branch feature/nueva_funcionalidad</li>
<li>git checkout feature/nueva_funcionalidad</li>
<li>--HICIMOS CAMBIOS EN EL README--</li>
<li>git add .</li>
<li>git commit -m "Se listaron los pasos realizados en el readme para la correccion del TP 1"</li>
<li>git push --set-upstream origin feature/nueva_funcionalidad</li>
<li>En la aplicacion web de github realizamos un pull request a develop con los cambios de feature/nueva_funcionalidad</li>
<li>Aceptamos el pull request</li>
<li>git checkout develop</li>
<li>git branch release/1.0.0</li>
<li>git checkout release/1.0.0</li>
<li>git add .</li>
<li>git push --set-upstream origin release/1.0.0</li>
<li>En la aplicacion web de github realizamos un pull request a main con los cambios de release/1.0.0</li>
<li>Aceptamos el pull request</li>
</ul>
