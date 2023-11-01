package com.tutorial.multiplemongodb;

import com.tutorial.multiplemongodb.entity.LawDocument;
import com.tutorial.multiplemongodb.entity.Music;
import com.tutorial.multiplemongodb.repository.crawler.LawDocumentRepository;
import com.tutorial.multiplemongodb.repository.musicmanager.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private LawDocumentRepository lawDocumentRepository;
    @Autowired
    private MusicRepository musicRepository;

    @Override
    public void run(String... args) throws Exception {
        Page<LawDocument> page = lawDocumentRepository.findAll(
                PageRequest.of(0, 100)
        );
        System.out.println(page.getContent().size());
        musicRepository.save(new Music(
            "Đêm trăng tình yêu", "Nguyễn Văn Dũng"
        ));
    }
}
