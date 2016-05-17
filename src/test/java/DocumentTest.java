import edw.olingo.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(BlockJUnit4ClassRunner.class)
public class DocumentTest {

    private static final String PERSISTENCE_UNIT_NAME = "persistence_unit";
    private EntityManagerFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Test
    public void testGetDocuments() throws Exception {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder qb = em.getCriteriaBuilder();
        List<Document> rows = em.createQuery(qb.createQuery(Document.class)).getResultList();
        org.junit.Assert.assertTrue(1 <= rows.size());
        em.close();
    }

    @Test
    public void testGetSingleDocument() throws Exception {
        EntityManager em = factory.createEntityManager();
        Document row = em.find(Document.class, "a180026b-a65b-4051-b00f-21c460f44bf8");

        assertNotNull(row);
        em.close();
    }

    @Test
    public void testDocumentAuthor() throws Exception {
        EntityManager em = factory.createEntityManager();
        Document row = em.find(Document.class, "72f32021-e8fe-4de7-8ca2-668f1474609a");
        assertNotNull(row);
        List<DocumentAuthor> authors = row.getAuthors();
        assertEquals(4, authors.size());

        em.close();
    }

    @Test
    public void testDocumentDescription() throws Exception {
        EntityManager em = factory.createEntityManager();
        Document row = em.find(Document.class, "72f32021-e8fe-4de7-8ca2-668f1474609a");
        assertNotNull(row);
        List<DocumentDescription> descriptions = row.getDescriptions();
        assertEquals(1, descriptions.size());

        em.close();
    }

    @Test
    public void testDocumentFile() throws Exception {
        EntityManager em = factory.createEntityManager();
        Document row = em.find(Document.class, "72f32021-e8fe-4de7-8ca2-668f1474609a");
        assertNotNull(row);
        List<DocumentFile> files = row.getFiles();
        assertEquals(1, files.size());

        em.close();
    }

    @Test
    public void testDocumentIdentifier() throws Exception {
        EntityManager em = factory.createEntityManager();
        Document row = em.find(Document.class, "72f32021-e8fe-4de7-8ca2-668f1474609a");
        assertNotNull(row);
        List<DocumentIdentifier> identifiers = row.getIdentifiers();
        System.out.println("identifiers" + identifiers.size());

        em.close();
    }

    @Test
    public void testDocumentKeyword() throws Exception {
        EntityManager em = factory.createEntityManager();
        Document row = em.find(Document.class, "72f32021-e8fe-4de7-8ca2-668f1474609a");
        assertNotNull(row);
        List<DocumentKeyword> keywords = row.getKeywords();
        System.out.println("keywords" + keywords.size());

        em.close();
    }

    public void testDocumentReference() throws Exception {
        try {
            EntityManager em = factory.createEntityManager();
            Document row = em.find(Document.class, "33529384-8c3a-416e-a8a7-ae5e307448be");
            assertNotNull(row);
            List<DocumentReference> references = row.getReferences();
            System.out.println("references" + references.size());
            assertEquals(1, references.size());

            em.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDocumentTag() throws Exception {
        EntityManager em = factory.createEntityManager();
        Document row = em.find(Document.class, "72f32021-e8fe-4de7-8ca2-668f1474609a");
        assertNotNull(row);
        List<DocumentTag> tags = row.getTags();
        System.out.println("tags" + tags.size());

        em.close();
    }

    @Test
    public void testDocumentTitle() throws Exception {
        EntityManager em = factory.createEntityManager();
        Document row = em.find(Document.class, "72f32021-e8fe-4de7-8ca2-668f1474609a");
        assertNotNull(row);
        List<DocumentTitle> titles = row.getTitles();
        assertEquals(1, titles.size());

        em.close();
    }

    @Test
    public void testDocumentType() throws Exception {
        EntityManager em = factory.createEntityManager();
        Document row = em.find(Document.class, "72f32021-e8fe-4de7-8ca2-668f1474609a");
        assertNotNull(row);
        List<DocumentType> types = row.getTypes();
        assertEquals(2, types.size());

        em.close();
    }
}
