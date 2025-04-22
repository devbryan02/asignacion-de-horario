package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.domain.enums.DiaSemana;
import elp.edu.pe.horario.domain.enums.ModoClase;
import elp.edu.pe.horario.domain.enums.TipoAula;
import elp.edu.pe.horario.domain.enums.Turno;
import elp.edu.pe.horario.domain.model.*;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/test")
public class HorarioSolverTestController {

    @GetMapping("/resolve")
    public HorarioSolucion generarEjemplo(){

        //Aulas
        Aula aulaLaboratorioInformatica = new Aula(UUID.randomUUID(), "B201", 30, TipoAula.LABORATORIO);
        Aula aulaClaseTeorica = new Aula(UUID.randomUUID(), "B203", 40, TipoAula.TEORICO);

        //Bloques de Horario
        BloqueHorario bloqueLunes8a10 = new BloqueHorario(UUID.randomUUID(),
                DiaSemana.LUNES,
                LocalTime.of(8,0),
                LocalTime.of(10,0),
                Turno.MANANA);

        BloqueHorario bloqueMartes10a12 = new BloqueHorario(UUID.randomUUID(),
                DiaSemana.MARTES,
                LocalTime.of(10,0),
                LocalTime.of(12,0),
                Turno.MANANA);

        BloqueHorario bloqueMiercoles8a10 = new BloqueHorario(UUID.randomUUID(),
                DiaSemana.MIERCOLES,
                LocalTime.of(8,0),
                LocalTime.of(10,0),
                Turno.MANANA);

        //Docentes
        Docente docenteMarcoPolo = new Docente(UUID.randomUUID(), "Dr. Marco Polo", 20);
        Docente docenteEliasRodriguez = new Docente(UUID.randomUUID(), "Mg. Elias Rodriguez", 15);

        //UnidadAcademica
        UnidadAcademica escuelaIngenieriaComputacion = new UnidadAcademica(UUID.randomUUID(), "Escuela de Ingeniería de Computación");

        //Cursos
        Curso cursoAlgebraLineal = new Curso(UUID.randomUUID(),
                "Álgebra Lineal",
                4,
                "Curso teórico-práctico de álgebra",
                escuelaIngenieriaComputacion);

        Curso cursoFisicaGeneral = new Curso(UUID.randomUUID(),
                "Física General",
                5,
                "Curso de fundamentos de física",
                escuelaIngenieriaComputacion);

        //Periodos Académicos
        PeriodoAcademico periodoSemestre2023I = new PeriodoAcademico(UUID.randomUUID(),
                "2023-I",
                LocalDate.of(2023, 3, 15),
                LocalDate.of(2023, 7, 15));

        PeriodoAcademico periodoSemestre2023II = new PeriodoAcademico(UUID.randomUUID(),
                "2023-II",
                LocalDate.of(2023, 8, 15),
                LocalDate.of(2023, 12, 15));

        //Secciones
        Seccion seccionMatematicasA = new Seccion(UUID.randomUUID(), "A", periodoSemestre2023I);
        Seccion seccionFisicaB = new Seccion(UUID.randomUUID(), "B", periodoSemestre2023II);

        //Modos de Clase
        ModoClase modoPresencial = ModoClase.PRESENCIAL;
        ModoClase modoVirtual = ModoClase.VIRTUAL;

        //Cursos-Sección
        CursoSeccion algebraLinealSeccionA = new CursoSeccion(UUID.randomUUID(), cursoAlgebraLineal, seccionMatematicasA, modoPresencial);
        CursoSeccion fisicaGeneralSeccionB = new CursoSeccion(UUID.randomUUID(), cursoFisicaGeneral, seccionFisicaB, modoVirtual);

        //Asignaciones sin aula ni bloque (OptaPlanner los asignará)
        AsignacionHorario asignacionAlgebra = new AsignacionHorario(
                UUID.randomUUID(), docenteMarcoPolo, algebraLinealSeccionA, null, null
        );
        AsignacionHorario asignacionFisica = new AsignacionHorario(
                UUID.randomUUID(), docenteEliasRodriguez, fisicaGeneralSeccionB, null, null
        );

        return new HorarioSolucion(
                Arrays.asList(aulaLaboratorioInformatica, aulaClaseTeorica),
                Arrays.asList(bloqueLunes8a10, bloqueMartes10a12, bloqueMiercoles8a10),
                Arrays.asList(docenteMarcoPolo, docenteEliasRodriguez),
                Arrays.asList(algebraLinealSeccionA, fisicaGeneralSeccionB),
                Arrays.asList(asignacionAlgebra, asignacionFisica)
        );
    }
}
