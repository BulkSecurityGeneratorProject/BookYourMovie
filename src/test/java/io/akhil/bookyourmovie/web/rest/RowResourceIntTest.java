package io.akhil.bookyourmovie.web.rest;

import io.akhil.bookyourmovie.BookYourMovieApp;

import io.akhil.bookyourmovie.domain.Row;
import io.akhil.bookyourmovie.repository.RowRepository;
import io.akhil.bookyourmovie.service.RowService;
import io.akhil.bookyourmovie.service.dto.RowDTO;
import io.akhil.bookyourmovie.service.mapper.RowMapper;
import io.akhil.bookyourmovie.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.akhil.bookyourmovie.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RowResource REST controller.
 *
 * @see RowResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookYourMovieApp.class)
public class RowResourceIntTest {

    private static final Integer DEFAULT_SERIAL_NUMBER = 1;
    private static final Integer UPDATED_SERIAL_NUMBER = 2;

    private static final Integer DEFAULT_START_POS = 1;
    private static final Integer UPDATED_START_POS = 2;

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RowRepository rowRepository;

    @Autowired
    private RowMapper rowMapper;
    
    @Autowired
    private RowService rowService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRowMockMvc;

    private Row row;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RowResource rowResource = new RowResource(rowService);
        this.restRowMockMvc = MockMvcBuilders.standaloneSetup(rowResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Row createEntity(EntityManager em) {
        Row row = new Row()
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .startPos(DEFAULT_START_POS)
            .price(DEFAULT_PRICE)
            .name(DEFAULT_NAME);
        return row;
    }

    @Before
    public void initTest() {
        row = createEntity(em);
    }

    @Test
    @Transactional
    public void createRow() throws Exception {
        int databaseSizeBeforeCreate = rowRepository.findAll().size();

        // Create the Row
        RowDTO rowDTO = rowMapper.toDto(row);
        restRowMockMvc.perform(post("/api/rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rowDTO)))
            .andExpect(status().isCreated());

        // Validate the Row in the database
        List<Row> rowList = rowRepository.findAll();
        assertThat(rowList).hasSize(databaseSizeBeforeCreate + 1);
        Row testRow = rowList.get(rowList.size() - 1);
        assertThat(testRow.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testRow.getStartPos()).isEqualTo(DEFAULT_START_POS);
        assertThat(testRow.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testRow.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rowRepository.findAll().size();

        // Create the Row with an existing ID
        row.setId(1L);
        RowDTO rowDTO = rowMapper.toDto(row);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRowMockMvc.perform(post("/api/rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rowDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Row in the database
        List<Row> rowList = rowRepository.findAll();
        assertThat(rowList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSerialNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = rowRepository.findAll().size();
        // set the field null
        row.setSerialNumber(null);

        // Create the Row, which fails.
        RowDTO rowDTO = rowMapper.toDto(row);

        restRowMockMvc.perform(post("/api/rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rowDTO)))
            .andExpect(status().isBadRequest());

        List<Row> rowList = rowRepository.findAll();
        assertThat(rowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartPosIsRequired() throws Exception {
        int databaseSizeBeforeTest = rowRepository.findAll().size();
        // set the field null
        row.setStartPos(null);

        // Create the Row, which fails.
        RowDTO rowDTO = rowMapper.toDto(row);

        restRowMockMvc.perform(post("/api/rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rowDTO)))
            .andExpect(status().isBadRequest());

        List<Row> rowList = rowRepository.findAll();
        assertThat(rowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = rowRepository.findAll().size();
        // set the field null
        row.setPrice(null);

        // Create the Row, which fails.
        RowDTO rowDTO = rowMapper.toDto(row);

        restRowMockMvc.perform(post("/api/rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rowDTO)))
            .andExpect(status().isBadRequest());

        List<Row> rowList = rowRepository.findAll();
        assertThat(rowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rowRepository.findAll().size();
        // set the field null
        row.setName(null);

        // Create the Row, which fails.
        RowDTO rowDTO = rowMapper.toDto(row);

        restRowMockMvc.perform(post("/api/rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rowDTO)))
            .andExpect(status().isBadRequest());

        List<Row> rowList = rowRepository.findAll();
        assertThat(rowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRows() throws Exception {
        // Initialize the database
        rowRepository.saveAndFlush(row);

        // Get all the rowList
        restRowMockMvc.perform(get("/api/rows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(row.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].startPos").value(hasItem(DEFAULT_START_POS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getRow() throws Exception {
        // Initialize the database
        rowRepository.saveAndFlush(row);

        // Get the row
        restRowMockMvc.perform(get("/api/rows/{id}", row.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(row.getId().intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.startPos").value(DEFAULT_START_POS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRow() throws Exception {
        // Get the row
        restRowMockMvc.perform(get("/api/rows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRow() throws Exception {
        // Initialize the database
        rowRepository.saveAndFlush(row);

        int databaseSizeBeforeUpdate = rowRepository.findAll().size();

        // Update the row
        Row updatedRow = rowRepository.findById(row.getId()).get();
        // Disconnect from session so that the updates on updatedRow are not directly saved in db
        em.detach(updatedRow);
        updatedRow
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .startPos(UPDATED_START_POS)
            .price(UPDATED_PRICE)
            .name(UPDATED_NAME);
        RowDTO rowDTO = rowMapper.toDto(updatedRow);

        restRowMockMvc.perform(put("/api/rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rowDTO)))
            .andExpect(status().isOk());

        // Validate the Row in the database
        List<Row> rowList = rowRepository.findAll();
        assertThat(rowList).hasSize(databaseSizeBeforeUpdate);
        Row testRow = rowList.get(rowList.size() - 1);
        assertThat(testRow.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testRow.getStartPos()).isEqualTo(UPDATED_START_POS);
        assertThat(testRow.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRow.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRow() throws Exception {
        int databaseSizeBeforeUpdate = rowRepository.findAll().size();

        // Create the Row
        RowDTO rowDTO = rowMapper.toDto(row);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRowMockMvc.perform(put("/api/rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rowDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Row in the database
        List<Row> rowList = rowRepository.findAll();
        assertThat(rowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRow() throws Exception {
        // Initialize the database
        rowRepository.saveAndFlush(row);

        int databaseSizeBeforeDelete = rowRepository.findAll().size();

        // Get the row
        restRowMockMvc.perform(delete("/api/rows/{id}", row.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Row> rowList = rowRepository.findAll();
        assertThat(rowList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Row.class);
        Row row1 = new Row();
        row1.setId(1L);
        Row row2 = new Row();
        row2.setId(row1.getId());
        assertThat(row1).isEqualTo(row2);
        row2.setId(2L);
        assertThat(row1).isNotEqualTo(row2);
        row1.setId(null);
        assertThat(row1).isNotEqualTo(row2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RowDTO.class);
        RowDTO rowDTO1 = new RowDTO();
        rowDTO1.setId(1L);
        RowDTO rowDTO2 = new RowDTO();
        assertThat(rowDTO1).isNotEqualTo(rowDTO2);
        rowDTO2.setId(rowDTO1.getId());
        assertThat(rowDTO1).isEqualTo(rowDTO2);
        rowDTO2.setId(2L);
        assertThat(rowDTO1).isNotEqualTo(rowDTO2);
        rowDTO1.setId(null);
        assertThat(rowDTO1).isNotEqualTo(rowDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rowMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rowMapper.fromId(null)).isNull();
    }
}
