package com.yt.utils;

import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 给论文创建索引
 */
public class IndexManager {

    private IndexWriter indexWriter;
    private IndexReader indexReader;
    private IndexSearcher indexSearcher;

    @Test
    public void main() throws Exception {
        Query query = new TermQuery(new Term("name", "asd2"));
        excQuery(query);
//        IndexManager.addDocument(Constants.INDEX_PATH, "E:/Spring/papers/target/papers-1.0-SNAPSHOT/upload/papers/42简历.docx");
    }

    @Test
    public void main2() throws Exception {
        addDocument(Constants.INDEX_PATH,Constants.PAPER_STORAGE_DIRECTORY_TWO+"asd2.docx");
    }
    /**
     * 向索引库中添加文档
     *
     * @param indexLibraryPath 指定索引库路径
     * @param readFilePath     要添加是文件的文件路径
     * @throws Exception
     */
    public static void addDocument(String indexLibraryPath, String readFilePath) throws Exception {
        //将索引库保存在内存
        Directory directory = FSDirectory.open(new File(indexLibraryPath).toPath());
        System.out.println(indexLibraryPath);
        //基于directory对象创建一个indexwriter对象
        Analyzer analyzer = new HanLPAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory,config);
        //读取磁盘上的文件
        File f = new File(readFilePath);
        //得到文件列表
            //获取文件名称
            String name = f.getName();
            //获取文件路径
            String path = f.getPath();
            String content = GetWorld.JudgingFileType(path);
            if (content.equals("err")){
                return;
            }
            content = content.replaceAll("[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]","");
            //获取文件大小
            long size = FileUtils.sizeOf(f);
            //创建Field,参数1：域的名称，参数2：域的内容， 参数3：是否存储
            Field fieldName = new TextField("name",name,Field.Store.YES);
            Field fieldPath = new TextField("path",path,Field.Store.YES);
            Field fieldSize = new TextField("size",size+"",Field.Store.YES);

            FieldType type = new FieldType();
            type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
            type.setStored(true);
            type.setStoreTermVectors(true);
            Field fieldContent = new Field("content",content,type);
            //创建文档对象
            Document document = new Document();
            //向文档对象中添加域
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldSize);
            document.add(fieldContent);
            //把文档对象写入索引库
            indexWriter.addDocument(document);
            indexWriter.close();
    }

    /**
     * 删除索引库中全部文档
     *
     * @param indexLibraryPath 指定索引库路径
     * @throws Exception
     */
    public static void deleteAllDocument(String indexLibraryPath) {
        try {
            IndexWriter indexWriter = new IndexWriter(FSDirectory.
                    open(new File(indexLibraryPath).toPath()),
                    new IndexWriterConfig(new HanLPAnalyzer()));
            //删除全部文档
            indexWriter.deleteAll();
            //关闭索引库
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据文件名中的关键字删除索引库中指定文档
     *
     * @param Term             文件名或者文件名中的关键字
     * @param indexLibraryPath 指定索引库路径
     * @param Field            域  ps: name ：文件名 ， path ：文件路径 ，content ：文件内容
     * @throws Exception
     */
    public static void deleteDocumentByQuery(String indexLibraryPath, String Term, String Field) {
        try {
            IndexWriter indexWriter = new IndexWriter(FSDirectory.
                    open(new File(indexLibraryPath).toPath()), new IndexWriterConfig(new HanLPAnalyzer()));
            indexWriter.deleteDocuments(new Term(Field, Term));
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新索引库
     *
     * @param indexLibraryPath 指定索引库路径
     * @throws Exception
     */
    public static void updateDelete(String indexLibraryPath) throws Exception {
        Document document = new Document();
        IndexWriter indexWriter = new IndexWriter(FSDirectory.
                open(new File(indexLibraryPath).toPath()), new IndexWriterConfig(new HanLPAnalyzer()));
        document.add(new TextField("Field", "text", Field.Store.YES));
        //更新操作先删除在添加
        indexWriter.updateDocument(new Term("Field", "Term"), document);
    }

    public void excQuery(Query query) throws Exception {
        indexReader = DirectoryReader.open(FSDirectory.
                open(new File(GetWorld.indexLibraryPath).toPath()));
        indexSearcher = new IndexSearcher(indexReader);
        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println(topDocs.totalHits);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = indexReader.document(scoreDoc.doc);
            System.out.println(doc.get("name") + "---" + doc.get("path"));
        }
        indexReader.close();
    }
}
