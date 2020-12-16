package com.codeoftheweb.Salvo;

import com.codeoftheweb.Salvo.model.*;
import com.codeoftheweb.Salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.WebAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class SalvoApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,GameRepository gameRepository,GamePlayerRepository gamePlayerRepository,ShipRepository shipRepository,SalvoRepository salvoRepository,ScoreRepository scoreRepository )
	{
		return (args) ->
		{
			//playerRepository.save(new Player("Alejandro Garro","alejandro@gmail.com",passwordEncoder().encode("123")));


			playerRepository.save(new Player("Jack Bauer","j.bauer@ctu.gov",passwordEncoder().encode("123")));
			playerRepository.save(new Player( "Chloe O'Brian","c.obrian@ctu.gov",passwordEncoder().encode("1234")));
			playerRepository.save(new Player("Kim Bauer","kim_bauer@gmail.com",passwordEncoder().encode("12345")));
			playerRepository.save(new Player( "Tony Almeidar","t.almeida@ctu.gov",passwordEncoder().encode("123456")));

			//Game 1
			gameRepository.save(new Game(LocalDateTime.now()));
			//Game 2
			//gameRepository.save(new Game(LocalDateTime.now().plusHours(1)));
			//Game 3
			//gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));

			//GamePlayer 1
			gamePlayerRepository.save(new GamePlayer(gameRepository.findById(1L).get(),playerRepository.findById(1L).get()));
			gamePlayerRepository.save(new GamePlayer(gameRepository.findById(1L).get(),playerRepository.findById(2L).get()));

			//GamePlayer 2
		//	gamePlayerRepository.save(new GamePlayer(gameRepository.findById(2L).get(),playerRepository.findById(3L).get()));
			//gamePlayerRepository.save(new GamePlayer(gameRepository.findById(2L).get(),playerRepository.findById(4L).get()));

			//GamePlayer 3
			/*gamePlayerRepository.save(new GamePlayer(gameRepository.findById(3L).get(),playerRepository.findById(1L).get()));
			gamePlayerRepository.save(new GamePlayer(gameRepository.findById(3L).get(),playerRepository.findById(3L).get()));*/


			//Ships gamePlayer 1
			/*shipRepository.save(new Ship("carrier", List.of("H1","H2","H3","H4","H5"),gamePlayerRepository.findById(1L).get()));
			shipRepository.save(new Ship("battleship", List.of("J1","J2","J3","J4"),gamePlayerRepository.findById(1L).get()));
			shipRepository.save(new Ship("submarine", List.of("C6","D6","E6"),gamePlayerRepository.findById(1L).get()));
			shipRepository.save(new Ship("destroyer", List.of("F1","F2","F3"),gamePlayerRepository.findById(1L).get()));
			shipRepository.save(new Ship("patrolBoat", List.of("E10","F10"),gamePlayerRepository.findById(1L).get()));

			//Ships gamePlayer 2
			shipRepository.save(new Ship("carrier", List.of("A1","B1","C1","D1","E1"),gamePlayerRepository.findById(2L).get()));
			shipRepository.save(new Ship("battleship", List.of("G3","G4","G5","G6"),gamePlayerRepository.findById(2L).get()));
			shipRepository.save(new Ship("submarine", List.of("H1","H2","H3"),gamePlayerRepository.findById(2L).get()));
			shipRepository.save(new Ship("destroyer", List.of("F2","F3","F4"),gamePlayerRepository.findById(2L).get()));
			shipRepository.save(new Ship("patrolBoat", List.of("A10","B10"),gamePlayerRepository.findById(2L).get()));
*/
			//Ships gamePlayer 3
			/*shipRepository.save(new Ship("Carrier", List.of("A1","A2","A3","A4","A5"),gamePlayerRepository.findById(3L).get()));
			shipRepository.save(new Ship("Battleship", List.of("B1","B2","B3","B4"),gamePlayerRepository.findById(3L).get()));
			shipRepository.save(new Ship("Submarine", List.of("C1","C2","C3"),gamePlayerRepository.findById(3L).get()));
			shipRepository.save(new Ship("Destroyer", List.of("D1","D2","D3"),gamePlayerRepository.findById(3L).get()));
			shipRepository.save(new Ship("PatrolBoat", List.of("E1","E2"),gamePlayerRepository.findById(3L).get()));*/


			salvoRepository.save(new Salvo(1L,List.of("B2","C1"),gamePlayerRepository.findById(1L).get()));
			salvoRepository.save(new Salvo(2L,List.of("B6","B7","B8","B9"),gamePlayerRepository.findById(1L).get()));
			salvoRepository.save(new Salvo(3L,List.of("B6","B7","B8","B9"),gamePlayerRepository.findById(1L).get()));
			salvoRepository.save(new Salvo(4L,List.of("E10","F10","G10"),gamePlayerRepository.findById(1L).get()));
			salvoRepository.save(new Salvo(5L,List.of("F2","F3","F4"),gamePlayerRepository.findById(1L).get()));
			salvoRepository.save(new Salvo(6L,List.of("I4","I5","I6","I7","I8"),gamePlayerRepository.findById(1L).get()));

			salvoRepository.save(new Salvo(1L,List.of("B2","C1"),gamePlayerRepository.findById(2L).get()));
			salvoRepository.save(new Salvo(2L,List.of("B6","B7","B8","B9"),gamePlayerRepository.findById(2L).get()));
			salvoRepository.save(new Salvo(3L,List.of("B6","B7","B8","B9"),gamePlayerRepository.findById(2L).get()));
			salvoRepository.save(new Salvo(4L,List.of("E10","F10","G10"),gamePlayerRepository.findById(2L).get()));
			salvoRepository.save(new Salvo(5L,List.of("F2","F3","F4"),gamePlayerRepository.findById(2L).get()));
			salvoRepository.save(new Salvo(6L,List.of("I4","I5","I6","I7","I8"),gamePlayerRepository.findById(2L).get()));

			/*salvoRepository.save(new Salvo(1L,List.of("F1","G2","E6"),gamePlayerRepository.findById(2L).get()));
			salvoRepository.save(new Salvo(2L,List.of("A10","H4","C2"),gamePlayerRepository.findById(2L).get()));

			salvoRepository.save(new Salvo(1L,List.of("D1","D2","C2","C7","A2"),gamePlayerRepository.findById(3L).get()));
			salvoRepository.save(new Salvo(2L,List.of("E1","E2","E3","E4","E5"),gamePlayerRepository.findById(3L).get()));


			//scoreRepository.save(gamePlayerRepository.findById(1L).get().getScore(1.0D));
			//scoreRepository.save(gamePlayerRepository.findById(1L).get().getScore(0.5D));
			//scoreRepository.save(gamePlayerRepository.findById(2L).get().getScore(0.5D));
			//scoreRepository.save(gamePlayerRepository.findById(2L).get().getScore(0.5D));
			//scoreRepository.save(gamePlayerRepository.findById(3L).get().getScore(1.0D));
			//scoreRepository.save(gamePlayerRepository.findById(3L).get().getScore(1.0D));*/
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter
{
	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByEmail(inputName);
			if (player != null) {
				System.out.println("Player found!\n");
				return new User(player.getEmail(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				System.out.println("Player not found!\n");
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/web/**").permitAll()
			.antMatchers("/api/**").permitAll()
			.antMatchers("/api/game_view").hasAuthority("USER");
		http.formLogin()
				.usernameParameter("name")
				.passwordParameter("pwd")
				.loginPage("/api/login");
		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}