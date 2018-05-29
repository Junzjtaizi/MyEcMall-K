package cn.nieking.goodscenter.injection.module

import cn.nieking.goodscenter.service.CategoryService
import cn.nieking.goodscenter.service.impl.CategoryServiceImpl
import dagger.Module
import dagger.Provides

@Module
class CategoryModule {

    @Provides
    fun providesCategoryService(service: CategoryServiceImpl): CategoryService {
        return service
    }
}