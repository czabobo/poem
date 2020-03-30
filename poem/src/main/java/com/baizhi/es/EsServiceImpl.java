package com.baizhi.es;

import com.baizhi.dao.PoemDao;
import com.baizhi.entity.Poem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EsServiceImpl implements EsService {
    @Autowired
    private PoemRepository poemRepository;
    @Autowired
    private PoemDao poemDao;

    @Override
    public void addEs() {
        List<Poem> poems = poemDao.selectAll();
        for (Poem poem : poems) {
            poemRepository.save(poem);
        }
    }

    @Override
    public void deleteEs() {
        List<Poem> poems = poemDao.selectAll();
        for (Poem poem : poems) {
            poemRepository.delete(poem);
        }
    }
}
