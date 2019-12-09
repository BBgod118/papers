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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 论文查重索引创建
 */
public class CreateIndex {

    /**
     * 创建索引库
     * @param indexLibraryPath 索引库保存路径
     * @param readFilePath  读取的文件夹路径  ps：作用是将该路径下的文件写入索引库
     * @throws Exception
     */
    public static void createIndex(String indexLibraryPath,String readFilePath) throws Exception{

        if(Files.isDirectory(Paths.get(indexLibraryPath))){
            //将索引库保存在内存
            Directory directory = FSDirectory.open(new File(indexLibraryPath).toPath());
            //基于directory对象创建一个indexwriter对象
            Analyzer analyzer = new HanLPAnalyzer();
            //IKAnalyzer是中文分析器  hotWord.dic是词库  stopword.dic是停用词库  两个词库可以自行添加词
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory,config);
            //读取磁盘上的文件
            File dir = new File(readFilePath);
            //得到文件列表
            File[] files = dir.listFiles();
            for(File f : files){
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
            }
            indexWriter.close();
        }else {
            System.out.println("该路径不是目录");
        }
    }


    /**
     * 查询索引库
     * @param indexLibraryPath 指定索引库的位置
     * @param field 域    ps：name ：文件名 ， path ：文件路径，content ： 文件内容，size ：文件大小
     * @param text  要查询的关键字
     * @throws Exception
     */
    public static void serchIndex(String indexLibraryPath, String field , String text ) throws Exception{
        //创建Directory对象，指定索引库位置
        Directory directory = FSDirectory.open(new File(indexLibraryPath).toPath());
        //创建IndexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建一个IndexSearcher对象，构造方法中的参数indexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //创建一个Query对象，TermQuery
        Query query = new TermQuery(new Term(field,text));
        //执行查询，得到一个TopDocs对象   参数一：查询对象 ，参数二:返回的最大记录数
        TopDocs topDocs =  indexSearcher.search(query , 10);
        //取查询结果的总记录数
        System.out.println("查询总记录数："+topDocs.totalHits);
        //取文档列表
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //打印文档中的内容
        for (ScoreDoc doc : scoreDocs){
            //取文档ID
            int docId = doc.doc;
            //根据ID取文档
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println("文档ID"+docId);
            System.out.println("----------------------");
        }
        indexReader.close();
    }
}
