package org.example.crud.ui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.crud.dao.*;
import org.example.crud.model.*;

public class MainController {

    // --- Cursos
    @FXML private TextField    tfCursoNome;
    @FXML private TextField    tfCursoSigla;
    @FXML private ChoiceBox<Area> cbCursoArea;
    @FXML private TableView<Curso> tvCursos;
    @FXML private TableColumn<Curso, Long>   colCursoId;
    @FXML private TableColumn<Curso, String> colCursoNome;
    @FXML private TableColumn<Curso, String> colCursoSigla;
    @FXML private TableColumn<Curso, Area>   colCursoAreaCol;

    private ICursoDAO cursoDAO = new CursoDAO();
    private ObservableList<Curso> cursosObs;

    // --- Alunos
    @FXML private TextField     tfAlunoMatricula;
    @FXML private TextField     tfAlunoNome;
    @FXML private CheckBox      cbAlunoMaioridade;
    @FXML private ChoiceBox<String> cbAlunoSexo;
    @FXML private ComboBox<Curso> cmbAlunoCurso;
    @FXML private TableView<Aluno> tvAlunos;
    @FXML private TableColumn<Aluno, Long>   colAlunoMatricula;
    @FXML private TableColumn<Aluno, String> colAlunoNome;
    @FXML private TableColumn<Aluno, Boolean>colAlunoMaior;
    @FXML private TableColumn<Aluno, String> colAlunoSexoCol;
    @FXML private TableColumn<Aluno, String> colAlunoCursoCol;

    private IAlunoDAO alunoDAO = new AlunoDAO();
    private ObservableList<Aluno> alunosObs;

    @FXML
    public void initialize() {
        // Popula ChoiceBox de áreas
        cbCursoArea.setItems(FXCollections.observableArrayList(Area.values()));

        // Colunas Cursos
        colCursoId.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getId()));
        colCursoNome.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getNome()));
        colCursoSigla.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getSigla()));
        colCursoAreaCol.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getArea()));

        // Carrega lista de cursos
        cursosObs = FXCollections.observableArrayList(cursoDAO.findAll());
        tvCursos.setItems(cursosObs);

        // Popula ComboBox de cursos para Aluno
        cmbAlunoCurso.setItems(cursosObs);

        // ChoiceBox de sexo
        cbAlunoSexo.setItems(FXCollections.observableArrayList("M","F","Outro"));

        // Colunas Alunos
        colAlunoMatricula.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getMatricula()));
        colAlunoNome.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getNome()));
        colAlunoMaior.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().isMaioridade()));
        colAlunoSexoCol.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getSexo()));
        colAlunoCursoCol.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().getCurso().getSigla()));

        // Carrega lista de alunos
        alunosObs = FXCollections.observableArrayList(alunoDAO.findall());
        tvAlunos.setItems(alunosObs);

        // Quando selecionar um curso na tabela, carrega no form
        tvCursos.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel!=null) {
                tfCursoNome.setText(sel.getNome());
                tfCursoSigla.setText(sel.getSigla());
                cbCursoArea.setValue(sel.getArea());
            }
        });

        // Quando selecionar um aluno na tabela
        tvAlunos.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel!=null) {
                tfAlunoMatricula.setText(String.valueOf(sel.getMatricula()));
                tfAlunoNome.setText(sel.getNome());
                cbAlunoMaioridade.setSelected(sel.isMaioridade());
                cbAlunoSexo.setValue(sel.getSexo());
                cmbAlunoCurso.setValue(sel.getCurso());
            }
        });
    }

    // --- Eventos Cursos ---
    @FXML
    private void onNovoCurso() {
        tfCursoNome.clear();
        tfCursoSigla.clear();
        cbCursoArea.setValue(null);
    }

    @FXML
    private void onSalvarCurso() {
        Curso c = new Curso(
                tfCursoNome.getText(),
                tfCursoSigla.getText(),
                cbCursoArea.getValue()
        );
        if (c.getId()==null) {
            cursoDAO.create(c);
            cursosObs.add(c);
        } else {
            cursoDAO.update(c);
            tvCursos.refresh();
        }
    }

    @FXML
    private void onExcluirCurso() {
        Curso sel = tvCursos.getSelectionModel().getSelectedItem();
        if (sel!=null) {
            cursoDAO.delete(sel.getSigla());
            cursosObs.remove(sel);
        }
    }

    // --- Eventos Alunos ---
    @FXML
    private void onNovoAluno() {
        tfAlunoMatricula.clear();
        tfAlunoNome.clear();
        cbAlunoMaioridade.setSelected(false);
        cbAlunoSexo.setValue(null);
        cmbAlunoCurso.setValue(null);
    }

    @FXML
    private void onSalvarAluno() {
        try {
            // 1) Validações básicas
            if (tfAlunoNome.getText().isBlank()) {
                new Alert(Alert.AlertType.WARNING, "Digite o nome do aluno.").showAndWait();
                return;
            }
            if (cbAlunoSexo.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Selecione o sexo do aluno.").showAndWait();
                return;
            }
            if (cmbAlunoCurso.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Selecione um curso para o aluno.").showAndWait();
                return;
            }

            // 2) Monta objeto Aluno
            long matricula = tfAlunoMatricula.getText().isEmpty()
                    ? 0L
                    : Long.parseLong(tfAlunoMatricula.getText());

            Aluno a = new Aluno(
                    tfAlunoNome.getText(),
                    cbAlunoMaioridade.isSelected(),
                    cmbAlunoCurso.getValue(),
                    matricula
            );
            a.setSexo(cbAlunoSexo.getValue());

            // 3) Persiste e atualiza a lista observável
            if (a.getMatricula() == 0L) {
                alunoDAO.create(a);
                alunosObs.add(a);
                new Alert(Alert.AlertType.INFORMATION,
                        "Aluno criado com matrícula: " + a.getMatricula())
                        .showAndWait();
            } else {
                alunoDAO.update(a);
                tvAlunos.refresh();
                new Alert(Alert.AlertType.INFORMATION,
                        "Dados do aluno atualizados com sucesso!")
                        .showAndWait();
            }

            // 4) Limpa o formulário
            onNovoAluno();

        } catch (NumberFormatException nfe) {
            new Alert(Alert.AlertType.ERROR,
                    "Matrícula inválida: " + nfe.getMessage())
                    .showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();  // veja o erro completo no console
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao salvar aluno:\n" + ex.getMessage())
                    .showAndWait();
        }
    }


    @FXML
    private void onExcluirAluno() {
        Aluno sel = tvAlunos.getSelectionModel().getSelectedItem();
        if (sel!=null) {
            alunoDAO.delete(sel.getMatricula());
            alunosObs.remove(sel);
        }
    }
}
