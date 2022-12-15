package br.com.felnanuke.bluetoothChat.di

import android.app.Application
import br.com.felnanuke.bluetoothChat.core.domain.repositories.MessageRepository
import br.com.felnanuke.bluetoothChat.core.domain.repositories.PairsRepository
import br.com.felnanuke.bluetoothChat.core.domain.repositories.ServerRepository
import br.com.felnanuke.bluetoothChat.core.infrastructure.nearby_connections.data_sources.NearbyConnectionsServerDataSource
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.AppRealm
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.data_sources.RealmMessagesDataSource
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.data_sources.RealmPairDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun providesServerRepository(
        serverDataSource: NearbyConnectionsServerDataSource,
        messageDataSource: RealmMessagesDataSource,
        pairDataSource: RealmPairDataSource
    ): ServerRepository {
        return ServerRepository(
            serverDataSource = serverDataSource, messageDataSource, pairDataSource
        )
    }

    @Provides
    @Singleton
    fun providesServerDataSource(
        application: Application,
        pairDataSource: RealmPairDataSource,
        messagesDataSource: RealmMessagesDataSource
    ): NearbyConnectionsServerDataSource {
        return NearbyConnectionsServerDataSource(
            application,
            pairDataSource,

            )
    }

    @Provides
    @Singleton
    fun providesPairDataSource(realm: Realm): RealmPairDataSource {
        return RealmPairDataSource(realm)
    }

    @Provides
    @Singleton
    fun providesMessagesDataSource(realm: Realm): RealmMessagesDataSource {
        return RealmMessagesDataSource(realm)
    }

    @Provides
    @Singleton
    fun providesAppRealm(): Realm {
        return AppRealm().realm
    }

    @Provides
    @Singleton
    fun providesPairsRepository(pairDataSource: RealmPairDataSource): PairsRepository {
        return PairsRepository(pairDataSource)
    }

    @Provides
    @Singleton
    fun providesMessagesRepository(messagesDataSource: RealmMessagesDataSource): MessageRepository {
        return MessageRepository(messagesDataSource)
    }


}