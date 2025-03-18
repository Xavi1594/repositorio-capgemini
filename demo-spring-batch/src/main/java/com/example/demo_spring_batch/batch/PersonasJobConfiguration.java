package com.example.demo_spring_batch.batch;

import java.io.IOException;
import java.io.Writer;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo_spring_batch.models.Persona;
import com.example.demo_spring_batch.models.PersonaDTO;
import com.example.demo_spring_batch.models.PhotoDTO;

@Configuration
public class PersonasJobConfiguration {

    private final Step photoStep;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    PlatformTransactionManager transactionManager;

    PersonasJobConfiguration(Step photoStep) {
        this.photoStep = photoStep;
    }

    public FlatFileItemReader<PersonaDTO> personaCSVItemReader(String fname) {
        return new FlatFileItemReaderBuilder<PersonaDTO>().name("personaCSVItemReader")
                .resource(new FileSystemResource(fname))
                .linesToSkip(1)
                .delimited()
                .names(new String[] { "id", "nombre", "apellidos", "correo", "sexo", "ip" })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<PersonaDTO>() {
                    {
                        setTargetType(PersonaDTO.class);
                    }
                })
                .build();
    }

    @Autowired
    public PersonaItemProcessor personaItemProcessor;

    @Bean
    @DependsOnDatabaseInitialization
    public JdbcBatchItemWriter<Persona> personaDBItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Persona>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO personas VALUES (:id,:nombre,:correo,:ip)")
                .dataSource(dataSource)
                .build();
    }

    Step importCSV2DBStep(int index, String file, JdbcBatchItemWriter<Persona> toDB) {
        return new StepBuilder("importCSV2DBStep" + index, jobRepository)
                .<PersonaDTO, Persona>chunk(10, transactionManager).reader(personaCSVItemReader(file))
                .processor(personaItemProcessor).writer(toDB).build();
    }

    // @Bean
    // public Job personasJob(PersonasJobListener listener,
    // JdbcBatchItemWriter<Persona> personaDBItemWriter) {
    // return new JobBuilder("personasJob", jobRepository).incrementer(new
    // RunIdIncrementer()).listener(listener)
    // .start(importCSV2DBStep(1, "input/personas-1.csv",
    // personaDBItemWriter)).build();
    // }

    // @Bean
    // JdbcCursorItemReader<Persona> personaDBItemReader(DataSource dataSource) {
    // return new JdbcCursorItemReaderBuilder<Persona>().name("personaDBItemReader")
    // .sql("SELECT id, nombre, correo, ip FROM personas").dataSource(dataSource)
    // .rowMapper(new BeanPropertyRowMapper<>(Persona.class))
    // .build();

    // }

    // @Bean
    // public FlatFileItemWriter<Persona> personaCSVItemWriter() {
    // return new FlatFileItemWriterBuilder<Persona>().name("personaCSVItemWriter")
    // .resource(new FileSystemResource("output/outputData.csv"))
    // .lineAggregator(new DelimitedLineAggregator<Persona>() {
    // }).build();
    // }

    @Bean
    JdbcCursorItemReader<Persona> personaDBItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Persona>().name("personaDBItemReader")
                .sql("SELECT id, nombre, correo, ip FROM personas").dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Persona.class))
                .build();
    }

    @Bean
    public FlatFileItemWriter<Persona> personaCSVItemWriter() {
        return new FlatFileItemWriterBuilder<Persona>().name("personaCSVItemWriter")
                .resource(new FileSystemResource("output/outputData.csv"))
                .lineAggregator(new DelimitedLineAggregator<Persona>() {
                    {// anonymous class constructor
                        setDelimiter(",");
                        setFieldExtractor(new BeanWrapperFieldExtractor<Persona>() {
                            {// anonymous class constructor
                                setNames(new String[] { "id", "nombre", "correo", "ip" });
                            }
                        });
                    }
                }).build();
    }

    @Bean
    public Step exportDB2CSVStep(JdbcCursorItemReader<Persona> personaDBItemReader) {
        return new StepBuilder("exportDB2CSVStep", jobRepository)
                .<Persona, Persona>chunk(100, transactionManager)
                .reader(personaDBItemReader)
                .writer(personaCSVItemWriter())
                .build();
    }

    // @Bean
    // public Job personasJob(PersonasJobListener listener,
    // JdbcBatchItemWriter<Persona> personaDBItemWriter,
    // Step exportDB2CSVStep) {
    // return new JobBuilder("personasJob", jobRepository)
    // .incrementer(new RunIdIncrementer())
    // .listener(listener)
    // .start(importCSV2DBStep(1, "input/personas-1.csv", personaDBItemWriter))
    // .next(exportDB2CSVStep)
    // .build();
    // }
    @Bean
    public FTPLoadTasklet ftpLoadTasklet(@Value("${input.dir.name:./ftp}") String dir) {
        FTPLoadTasklet tasklet = new FTPLoadTasklet();
        tasklet.setDirectoryResource(new FileSystemResource(dir));
        return tasklet;
    }

    @Bean
    public Step copyFilesInDir(FTPLoadTasklet ftpLoadTasklet) {
        return new StepBuilder("copyFilesInDir", jobRepository)
                .tasklet(ftpLoadTasklet, transactionManager)
                .build();
    }

    @Autowired
    PhotoRestItemReader photoRestItemReader;

    @Bean
    Step photoStep(JdbcCursorItemReader<Persona> personaDBItemReader) {
        String[] headers = new String[] { "id", "author", "width", "height", "url", "download_url" };
        return new StepBuilder("photoStep1", jobRepository)
                .<PhotoDTO, PhotoDTO>chunk(100, transactionManager).reader(photoRestItemReader)
                .writer(new FlatFileItemWriterBuilder<PhotoDTO>().name("photoCSVItemWriter")
                        .resource(new FileSystemResource("output/photoData.csv"))
                        .headerCallback(new FlatFileHeaderCallback() {
                            public void writeHeader(Writer writer) throws IOException {
                                writer.write(String.join(",", headers));
                            }
                        }).lineAggregator(new DelimitedLineAggregator<PhotoDTO>() {
                            {
                                setDelimiter(",");
                                setFieldExtractor(new BeanWrapperFieldExtractor<PhotoDTO>() {
                                    {
                                        setNames(headers);
                                    }
                                });
                            }
                        }).build())
                .build();
    }

    @Bean
    public Job personasJob(PersonasJobListener listener) {
        return new JobBuilder("personasJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(photoStep)
                .build();
    }
}
